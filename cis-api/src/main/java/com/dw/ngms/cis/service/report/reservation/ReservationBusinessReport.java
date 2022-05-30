package com.dw.ngms.cis.service.report.reservation;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.reservation.BusinessRuleProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.reservation.BusinessRuleProjectionRowMapper;
import com.dw.ngms.cis.service.report.GenericReport;
import com.dw.ngms.cis.utilities.ReportUtils;
import com.dw.ngms.cis.web.vm.report.ReportingVm;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 15/06/21, Tue
 **/
@Component
@Slf4j
public class ReservationBusinessReport implements GenericReport {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Autowired
    public ReservationBusinessReport(NamedParameterJdbcTemplate jdbcTemplate,
                          SpringTemplateEngine templateEngine,
                          ReportUtils reportUtils,
                          ProvinceRepository provinceRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.templateEngine = templateEngine;
        this.reportUtils = reportUtils;
        this.provinceRepository = provinceRepository;
    }

    @Override
    public Resource generateReport(ReportingVm reportingVm) {
        if ("PDF".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final String htmlReport = generateBusinessRuleHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "ReservationBusinessRule");
        } else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final List<BusinessRuleProjection> businessRuleProjection = generateReportData(reportingVm);
            return generateCsvResource(businessRuleProjection);
        }

        return null;
    }

    private String generateBusinessRuleHtmlReport(final ReportingVm reportingVm) {

        final Collection<BusinessRuleProjection> businessRuleProjection = generateReportData(reportingVm);
        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(BusinessRuleProjection.class);

        Context context = new Context();
        context.setVariable(DATA, businessRuleProjection);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("/reservation/business-rule.html", context);
    }

    private List<BusinessRuleProjection> generateReportData(final ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        String sql = "SELECT province as province," +
                "PROCESSNAME as processName, " +
                "moduleName as moduleName," +
                "DATED as Dated," +
                "USERNAME as userName, " +
                "CONTEXT as context," +
                "BUSINESSRULEDETAILS as businessRuleDetails " +
                "from VW_WH_BUSINESS_RULES  " +
                "WHERE   PROVINCEID in (:provinces) "+
                " AND itemidModuel = 250 " +
                " AND Dated between :fromDate and :toDate";
        return jdbcTemplate
                .query(sql, parameters, new BusinessRuleProjectionRowMapper());
    }

    private Resource generateCsvResource(List<BusinessRuleProjection> businessRuleProjection) {
        try {


            final File businessRuleReport = File.createTempFile("ReservationBusinessRuleReport", ".csv", null);
            final Writer writer = new FileWriter(businessRuleReport);

            StatefulBeanToCsv<BusinessRuleProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<BusinessRuleProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(businessRuleProjection);
            writer.close();

            return new FileSystemResource(businessRuleReport);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }
}
