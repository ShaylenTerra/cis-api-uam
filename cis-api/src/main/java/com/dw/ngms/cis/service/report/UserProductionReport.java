package com.dw.ngms.cis.service.report;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.UserProductionProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.UserProductionProjectionRowMapper;
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
public class UserProductionReport implements GenericReport {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Autowired
    public UserProductionReport(NamedParameterJdbcTemplate jdbcTemplate,
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
        if("PDF".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final String htmlReport = generateProductionHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "UserProduction");
        } else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final List<UserProductionProjection> userProductionProjection = generateReportData(reportingVm);
            return generateCsvResource(userProductionProjection);
        }

        return null;
    }

    private String generateProductionHtmlReport(final ReportingVm reportingVm) {

        final Collection<UserProductionProjection> userProductionProjection = generateReportData(reportingVm);
        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(UserProductionProjection.class);


        Context context = new Context();
        context.setVariable(DATA, userProductionProjection);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("user-production.html", context);
    }

    private List<UserProductionProjection> generateReportData(final ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        String sql = "SELECT REFERENCE_NO as referenceNumber," +
                "DATERECEIVED as dateReceived," +
                "PRODUCTCATEGORY as productCategory," +
                "PRODUCTITEMS as productItem," +
                "PRODUCT_QUANTITY as productQuantity," +
                "REQUESTOR as requester," +
                "REQUESTOR_ROLE as requesterRole," +
                "REQUESTOR_TYPE as requesterType," +
                "REQUESTOR_SECTOR as requesterSector," +
                "USER_RECIVEDON dateAllocated," +
                "USER_NAME as officer," +
                "USER_COMPLETEDON as dateCompleted," +
                "ACTION_CONEXT as actionContext," +
                "PRODUCTIVITY_MINUTES as productivityMinutes," +
                "INVOICE_AMOUNT as cost," +
                "INVOICE_NUMBER as invoiceNumber," +
                "INTERNALSTATUSCAPTION as status " +
                "from VW_WF_INFO_USER_PRODUCTIVITY " +
                "WHERE REQUESTOR_TYPE in (:userType) " +
                " AND data_province in (:provinces) " +
                " AND DATERECEIVED between :fromDate and :toDate";

        return jdbcTemplate
                .query(sql, parameters, new UserProductionProjectionRowMapper());
    }

    private Resource generateCsvResource(List<UserProductionProjection> userProductionProjection) {
        try {


            final File userProductionReport = File.createTempFile("userProductionReport", ".csv", null);
            final Writer writer = new FileWriter(userProductionReport);

            StatefulBeanToCsv<UserProductionProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<UserProductionProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(userProductionProjection);
            writer.close();

            return new FileSystemResource(userProductionReport);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }
}
