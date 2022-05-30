package com.dw.ngms.cis.service.report.reservation;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.BusinessRuleProjection;
import com.dw.ngms.cis.persistence.projection.report.reservation.UserReportProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.reservation.UserProjectionRowMapper;
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
 * @since : 2022/02/28, Mon
 **/
@Component
@Slf4j
public class UserReport implements GenericReport {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Autowired
    public UserReport(NamedParameterJdbcTemplate jdbcTemplate,
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
            final String htmlReport = generateReservationProductionHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "ReservationUserReport");
        } else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final List<UserReportProjection> reservationProductionProjections = generateReportData(reportingVm);
            return generateCsvResource(reservationProductionProjections);
        }

        return null;
    }
    private String generateReservationProductionHtmlReport(final ReportingVm reportingVm) {

        final Collection<UserReportProjection> reservationProductionProjections = generateReportData(reportingVm);
        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId;
            byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(BusinessRuleProjection.class);

        Context context = new Context();
        context.setVariable(DATA, reservationProductionProjections);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("/reservation/user-report.html", context);
    }
    private List<UserReportProjection> generateReportData(final ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);

                String sql = "Select\n" +
                "work.Request_Province_Name as provinceName,\n" +
                "work.Request_Province_ID as provinceId,\n" +
                "work.REFERENCE_NO as referenceNumber,\n" +
                "res.Parent_Designation as propertyDescription,\n" +
                "res.No_of_Request as noOfParentDesignation,\n" +
                "res.No_of_Issued as noOfRequestedDesignation,\n" +
                "res.RES_Type as reservationType,\n" +
                "res.RES_TYPE_ID as  reservationTypeId,\n" +
                "work.Task_Started as dateReceived,\n" +
                "res.APPLICANT_FullName as applicantFullName,\n" +
                "res.APPLICANT_ROLE as applicantRole,\n" +
                "res.Officer_Name as officerName,\n" +
                "res.Officer_Recived as officerRecived,\n" +
                "res.Officer_TimeTaken as officerTimeTaken,\n" +
                "res.QA_Name as scrutnizerName ,\n" +
                "res.QA_Recived as scrutnizerReceived,\n" +
                "res.QA_TimeTaken as scrutnizerTimeTaken,\n" +
                "work.Task_Total_Productivity as taskTotalProductivity,\n" +
                "work.Task_InternalStatus as taskInternalStatusId,\n" +
                "work.Task_InternalStatus_Caption as taskInternalStatusCaption,\n" +
                "res.APPLICANT_TYPE as applicantType,\n" +
                "res.APPLICANT_USERID as applicantUserId,\n" +
                "res.TOTALPRODUCTIVITY as totalProductivity,\n" +
                "res.ReservationDate as ReservationDate\n " +
                "from VW_WH_RESERVATION_REQUEST  res\n" +
                "inner join  vw_WH_WORKFLOW_MASTER work on res.WORKFLOWID = work.WORKFLOWID\n" +
                "where work.Config_PARENT_PROCESSID in(229)" +
                " AND  res.APPLICANT_TYPE in (:userType) " +
                " AND work.Request_Province_ID in (:provinces) " +
                " AND work.Task_Started between :fromDate and :toDate" ;

        return jdbcTemplate
                .query(sql, parameters, new UserProjectionRowMapper());
    }

    private Resource generateCsvResource(List<UserReportProjection> userReportProjections) {
        try {

            final File userReport = File.createTempFile("UserReport", ".csv", null);
            final Writer writer = new FileWriter(userReport);

            StatefulBeanToCsv<UserReportProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<UserReportProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(userReportProjections);
            writer.close();

            return new FileSystemResource(userReport);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }
}
