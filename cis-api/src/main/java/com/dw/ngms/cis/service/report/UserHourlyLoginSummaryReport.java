package com.dw.ngms.cis.service.report;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.UserHourlyLoginDistributionProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.UserHourlyLoginDistributionProjectionRowMapper;
import com.dw.ngms.cis.utilities.ReportUtils;
import com.dw.ngms.cis.web.vm.report.ReportingVm;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 15/06/21, Tue
 **/
@Component
@AllArgsConstructor
@Slf4j
public class UserHourlyLoginSummaryReport implements GenericReport {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Override
    public Resource generateReport(ReportingVm reportingVm) {

        if ("PDF".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final String htmlReport = generateUserLoginSummaryHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "UserHourlyLoginSummaryReport");
        } else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final List<UserHourlyLoginDistributionProjection> userHourlyLoginDistributionProjection = generateReportData(reportingVm);
            return generateCsvResource(userHourlyLoginDistributionProjection);
        }
        return null;
    }

    private String generateUserLoginSummaryHtmlReport(final ReportingVm reportingVm) {

        final List<UserHourlyLoginDistributionProjection> userLoginSummaryProjection = generateReportData(reportingVm);

        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(UserHourlyLoginDistributionProjection.class);

        Context context = new Context();
        context.setVariable(DATA, userLoginSummaryProjection);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("user-hourly-login-summary.html", context);
    }

    private List<UserHourlyLoginDistributionProjection> generateReportData(ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        String sql = "SELECT " +
                " TOTALUSER AS totalUser, " +
                " HOURS as hours " +
                "from V_USER_LOGIN_BYHOURS " +
                " WHERE  USERTYPE in (:userType) " +
                " AND PROVINCEID in (:provinces) " +
                " AND LOGINDATE between :fromDate and :toDate";


        return  jdbcTemplate
                .query(sql, parameters, new UserHourlyLoginDistributionProjectionRowMapper());
    }

    private Resource generateCsvResource(List<UserHourlyLoginDistributionProjection> userHourlyLoginDistributionProjection) {
        try {


            final File userHourlyLoginDistributionReport = File.createTempFile("userHourlyLoginDistributionReport", ".csv", null);
            final Writer writer = new FileWriter(userHourlyLoginDistributionReport);

            StatefulBeanToCsv<UserHourlyLoginDistributionProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<UserHourlyLoginDistributionProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(userHourlyLoginDistributionProjection);
            writer.close();

            return new FileSystemResource(userHourlyLoginDistributionReport);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }
}
