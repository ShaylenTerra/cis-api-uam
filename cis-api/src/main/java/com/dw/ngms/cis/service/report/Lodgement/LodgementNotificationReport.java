package com.dw.ngms.cis.service.report.Lodgement;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.Lodgement.LodgementNotificationReportProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.Lodgement.LodgementNotificationReportProjectionRowMapper;
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
    public class LodgementNotificationReport implements GenericReport {

        private final NamedParameterJdbcTemplate jdbcTemplate;

        private final SpringTemplateEngine templateEngine;

        private final ReportUtils reportUtils;

        private final ProvinceRepository provinceRepository;

        @Autowired
        public LodgementNotificationReport(NamedParameterJdbcTemplate jdbcTemplate,
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
                final String htmlReport = generateLodgementNotificationReportHtmlReport(reportingVm);
                return reportUtils.generatePdfOfReport(htmlReport, "NotificationReport");
            } else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
                final List<LodgementNotificationReportProjection> lodgementNotificationReportProjections = generateReportData(reportingVm);
                return generateCsvResource(lodgementNotificationReportProjections);
            }

            return null;
        }

        private String generateLodgementNotificationReportHtmlReport(final ReportingVm reportingVm) {

            final Collection<LodgementNotificationReportProjection> lodgementNotificationReportProjections = generateReportData(reportingVm);
            reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
                final Province byProvinceId = provinceRepository.findByProvinceId(aLong);
                if (null != byProvinceId)
                    return byProvinceId.getProvinceName();
                return null;
            }).collect(Collectors.toSet()));

            final List<String> columnName = reportUtils.getColumnName(LodgementNotificationReportProjection.class);

            Context context = new Context();
            context.setVariable(DATA, lodgementNotificationReportProjections);
            context.setVariable(REQUEST, reportingVm);
            context.setVariable(COLUMNS, columnName);
            return templateEngine.process("/Lodgement/LodgementNotificationReport.html", context);
        }

        private List<LodgementNotificationReportProjection> generateReportData(final ReportingVm reportingVm) {
            Map<String, Object> map = new HashMap();
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
                    " AND DATERECEIVED between :fromDate and :toDate"+
                    " AND PROCESSID in(278)";



            return jdbcTemplate
                    .query(sql, parameters, new LodgementNotificationReportProjectionRowMapper());
        }

        private Resource generateCsvResource(List<LodgementNotificationReportProjection> lodgementNotificationReportProjections) {
            try {


                final File lodgementNotificationReport = File.createTempFile("LodgementNotificationReport", ".csv", null);
                final Writer writer = new FileWriter(lodgementNotificationReport);
                StatefulBeanToCsv<LodgementNotificationReportProjection> beanToCsv =
                        new StatefulBeanToCsvBuilder<LodgementNotificationReportProjection>(writer)
                                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                                .withSeparator(',')
                                .build();

                beanToCsv.write(lodgementNotificationReportProjections);
                writer.close();

                return new FileSystemResource(lodgementNotificationReport);

            } catch (CsvException | IOException ex) {
                log.error("Error mapping Bean to CSV", ex);
                ex.printStackTrace();
            }

            return null;
        }
    }



