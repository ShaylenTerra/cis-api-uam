package com.dw.ngms.cis.service.report.Lodgement;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.Lodgement.LodgementBusinessReportProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.Lodgement.LodgementBusinessRuleProjectionRowMapper;
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
 * @author : AnkitSaxena
 * @since : 11/04/22, Mon
 **/
@Component
@Slf4j
public class LodgementBusinessRuleReport implements GenericReport {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Autowired
    public LodgementBusinessRuleReport(NamedParameterJdbcTemplate jdbcTemplate,
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
            final String htmlReport = generateLodgementBusinessRuleHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "BusinessRuleReports");
        } else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final List<LodgementBusinessReportProjection> lodgementBusinessReportProjections = generateReportData(reportingVm);
            return generateCsvResource(lodgementBusinessReportProjections);
        }

        return null;
    }
    private String generateLodgementBusinessRuleHtmlReport(final ReportingVm reportingVm) {

        final Collection<LodgementBusinessReportProjection> LodgementBusinessReportProjection = generateReportData(reportingVm);
        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId;
            byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(LodgementBusinessReportProjection.class);

        Context context = new Context();
        context.setVariable(DATA, LodgementBusinessReportProjection);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("/Lodgement/LodgementBusinessRuleReport.html", context);
    }
    private List<LodgementBusinessReportProjection> generateReportData(final ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);
        String sql = "Select province as ProvinceName, " +
                "processName as ProcessName , " +
                "moduleName as ModuleName , " +
                "Dated as Dated, " +
                "userName as userName, " +
                "context as context, " +
                "businessRuleDetails as BusinessRuleDetails " +
                " from VW_WH_BUSINESS_RULES " +
                " where PROVINCEID in (:provinces)" +
                " AND itemidModuel = 506" +
                " AND Dated between :fromDate and :toDate";

        return jdbcTemplate
                .query(sql, parameters, new LodgementBusinessRuleProjectionRowMapper());
    }

    private Resource generateCsvResource(List<LodgementBusinessReportProjection> LodgementBusinessReportProjection) {
        try {

            final File BusinessRuleReports = File.createTempFile("BusinessRuleReports", ".csv", null);
            final Writer writer = new FileWriter(BusinessRuleReports);

            StatefulBeanToCsv<LodgementBusinessReportProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<LodgementBusinessReportProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(LodgementBusinessReportProjection);
            writer.close();

            return new FileSystemResource(BusinessRuleReports);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }
}

