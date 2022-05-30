package com.dw.ngms.cis.service.report.reservation;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.reservation.ManagerNotificationProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.reservation.ManagerNotificationProjectionRowMapper;
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
 * @author : pragayanshu
 * @since : 15/06/21, Tue
 **/
@Component
@Slf4j
public class ReservationNotificationReport implements GenericReport {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Autowired
    public ReservationNotificationReport(NamedParameterJdbcTemplate jdbcTemplate,
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
            final String htmlReport = generateManagerNotificationHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "ReservationManagerNotification");
        }
    else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
        final List<ManagerNotificationProjection> managerNotificationProjection = generateReportData(reportingVm);
        return generateCsvResource(managerNotificationProjection);
    }

        return null;
    }

    private String generateManagerNotificationHtmlReport(final ReportingVm reportingVm) {

        final Collection<ManagerNotificationProjection> managerNotificationProjection = generateReportData(reportingVm);
        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(ManagerNotificationProjection.class);


        Context context = new Context();
        context.setVariable(DATA, managerNotificationProjection);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("/reservation/manager-notification.html", context);
    }

    private List<ManagerNotificationProjection> generateReportData(final ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        String sql = "SELECT REFERENCE_NO as referenceNumber," +
                "SUB_REFERENCE_NO as subReferenceNumber," +
                "DATERECEIVED as dateReceived," +
                "PRODUCTCATEGORY as propertyDescription," +
                "DATEALLOCATED as dateAllocated," +
                "USER_NAME as userName," +
                "USER_ROLE as userRole," +
                "DATERECEIVED as dateOfficerNotified," +
                "NOTIFICATION_TYPE as notificationType," +
                "USERCOMMENTS as userComments," +
                "INTERNALSTATUSCAPTION as status " +
                "from VW_WF_NOTIFICATIONS " +
                "WHERE USERTYPE in (:userType) " +
                " AND data_province in (:provinces) " +
                " AND DATERECEIVED between :fromDate and :toDate" +
                " AND PROCESSID in(229,239)";

        return jdbcTemplate
                .query(sql, parameters, new ManagerNotificationProjectionRowMapper());
    }

    private Resource generateCsvResource(List<ManagerNotificationProjection> managerNotificationProjection) {
        try {


            final File managerNotificationReport = File.createTempFile("ReservationManagerNotificationReport", ".csv", null);
            final Writer writer = new FileWriter(managerNotificationReport);

            StatefulBeanToCsv<ManagerNotificationProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<ManagerNotificationProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(managerNotificationProjection);
            writer.close();

            return new FileSystemResource(managerNotificationReport);

        }
        catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }
}
