package com.dw.ngms.cis.service.report;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.RequestStatisticalProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.RequestStatisticalProjectionRowMapper;
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
public class RequestStatisticalReport implements GenericReport {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Autowired
    public RequestStatisticalReport(NamedParameterJdbcTemplate jdbcTemplate,
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
            final String htmlReport = generateRequestStatisticalHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "RequestStatistics");
        }
        else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final List<RequestStatisticalProjection> requestStatisticalProjection = generateReportData(reportingVm);
            return generateCsvResource(requestStatisticalProjection);
        }
        return null;
    }

    private String generateRequestStatisticalHtmlReport(final ReportingVm reportingVm) {

        final Collection<RequestStatisticalProjection> requestStatisticalProjection = generateReportData(reportingVm);
        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(RequestStatisticalProjection.class);


        Context context = new Context();
        context.setVariable(DATA, requestStatisticalProjection);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("request-statistical.html", context);
    }

    private List<RequestStatisticalProjection> generateReportData(final ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        String sql = "SELECT ITEM as item," +
                "DETAILS as details," +
                "data_province as provinceId," +
                "data_province_name as provinceName," +
                "sum(numberofrequest) as numberofrequest " +
                "from vw_wf_request_items " +
                "WHERE data_province in (:provinces) " +
                " AND DATERECEIVED between :fromDate and :toDate" +
                " GROUP by ITEM, DETAILS, DATA_PROVINCE,DATA_PROVINCE_NAME";

        return jdbcTemplate
                .query(sql, parameters, new RequestStatisticalProjectionRowMapper());
    }

    private Resource generateCsvResource(List<RequestStatisticalProjection> requestStatisticalProjection) {
        try {


            final File requestStatisticalReport = File.createTempFile("requestStatisticalReport", ".csv", null);
            final Writer writer = new FileWriter(requestStatisticalReport);

            StatefulBeanToCsv<RequestStatisticalProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<RequestStatisticalProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(requestStatisticalProjection);
            writer.close();

            return new FileSystemResource(requestStatisticalReport);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }
}
