package com.dw.ngms.cis.service.report.Lodgement;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.Lodgement.LodgementProductionReportProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.Lodgement.LodgementProductionReportProjectionRowMapper;
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
public class LodgementProductionReport implements GenericReport {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Autowired
    public LodgementProductionReport(NamedParameterJdbcTemplate jdbcTemplate,
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
            final String htmlReport = generateLodgementProductionHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "ProductionReport");
        } else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final List<LodgementProductionReportProjection> lodgementproductionReportProjections = generateReportData(reportingVm);
            return generateCsvResource(lodgementproductionReportProjections);
        }

        return null;
    }

    private String generateLodgementProductionHtmlReport(final ReportingVm reportingVm) {

        final Collection<LodgementProductionReportProjection> lodgementProductionReportProjections = generateReportData(reportingVm);
        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(LodgementProductionReportProjection.class);

        Context context = new Context();
        context.setVariable(DATA, lodgementProductionReportProjections);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("/Lodgement/LodgementProductionReport.html", context);
    }

    private List<LodgementProductionReportProjection> generateReportData(final ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        String sql = "Select work.REQUEST_PROVINCE_NAME as Province_Name, "+
        "work.REQUEST_PROVINCE_ID as Province_ID, "+
        "work.REFERENCE_NO as lodgementReference, "+
        "ldg.PARENT_DESIGNATION as parcelDescription, "+
         "ldg.NO_OF_REQUEST as NoOfParentDesignation, "+
        "ldg.NO_OF_ISSUED as NoOfRequestedDesignation, "+
        "ldg.LDG_PURPOSE as lodgementType, "+
        "ldg.LDG_PURPOSE_ID as  LDG_PURPOSE_ID, "+
        "work.TASK_STARTED as dateSubmitted, "+
        "ldg.APPLICANT_FULLNAME as professionalpractitioner, "+
        "ldg.APPLICANT_ROLE, "+
        "ldg.OFFICER_NAME as Officer, "+
        "ldg.OFFICER_RECIVED as dateLodged, "+
        "ldg.OFFICER_TIMETAKEN as turnAroundTime, "+
        "ldg.QA_NAME as scrutinizer, "+
        "ldg.QA_RECIVED, "+
        "ldg.QA_TIMETAKEN, "+
        "work.TASK_TOTAL_PRODUCTIVITY, "+
        "ldg.TOTALPRODUCTIVITY, "+
        "work.TASK_INTERNALSTATUS , "+
        "work.TASK_INTERNALSTATUS_CAPTION as status, "+
        "ldg.APPLICANT_TYPE, "+
        "ldg.APPLICANT_USERID, "+
        "ldg.BATCHNO as Batch, "+
        "ldg.SGNUMBER as SG, "+
        "ldg.PAYMENTMETHOD as paymentMethod, "+
        "ldg.RECEIPTNUMBER as receiptNumber, "+
        "ldg.DATEVERIFIED as dateVerified, "+
        "ldg.AMOUNTPAID as amountPaid, "+
         " LDG_SUBTYPE as lodgementSubType,  "+
         " No_of_Request as ervens, "+
        "ldg.SRNUUMBER as sRNumber "+
       " from VW_WH_LODGEMENT_REQUEST  ldg inner join  vw_WH_WORKFLOW_MASTER work " +
          "  on ldg.WORKFLOWID = work.WORKFLOWID "+
          "where work.CONFIG_PARENT_PROCESSID in(278) "+
        " AND  ldg.APPLICANT_TYPE in (:userType) " +
        " AND work.Request_Province_ID in (:provinces) " +
        " AND work.Task_Started between :fromDate and :toDate" ;



        return jdbcTemplate
                .query(sql, parameters, new LodgementProductionReportProjectionRowMapper());
    }

    private Resource generateCsvResource(List<LodgementProductionReportProjection> lodgementProductionReportProjections) {
        try {


            final File lodgementProjectionReport = File.createTempFile("ProductionReport", ".csv", null);
            final Writer writer = new FileWriter(lodgementProjectionReport);
            StatefulBeanToCsv<LodgementProductionReportProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<LodgementProductionReportProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(lodgementProductionReportProjections);
            writer.close();

            return new FileSystemResource(lodgementProjectionReport);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }
}