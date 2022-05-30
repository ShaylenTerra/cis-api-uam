package com.dw.ngms.cis.service.report;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.UserSummaryProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.UserSummaryProjectionRowMapper;
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
@AllArgsConstructor
public class UserSummaryReport implements GenericReport {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Override
    public Resource generateReport(ReportingVm reportingVm) {
        if ("PDF".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final String htmlReport = generateUserSummaryHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "UserSummary");
        } else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final List<UserSummaryProjection> userSummaryProjections = generateReportData(reportingVm);
            return generateCsvResource(userSummaryProjections);
        }

        return null;
    }


    private String generateUserSummaryHtmlReport(final ReportingVm reportingVm) {

        final Collection<UserSummaryProjection> userSummaryProjections = generateReportData(reportingVm);
        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(UserSummaryProjection.class);

        userSummaryProjections.forEach(userSummaryProjection -> {

        });

        Context context = new Context();
        context.setVariable(DATA, userSummaryProjections);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("user-summary.html", context);
    }

    private List<UserSummaryProjection> generateReportData(final ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        String sql = "SELECT USERCREATIONDATE as userCreationDate," +
                "USERCREATIONDATE AS lastLoginDate, " +
                "FULLNAME as fullName," +
                "USERTYPE as userType," +
                "ORGANIZATION_NAME as organisation, " +
                "SECTOR_NAME as sector," +
                "ROLENAME as role," +
                "PROVINCE_NAME as province," +
                " EMAIL as userName, " +
                "STATUS as status, " +
                "ADDITIONAL_ROLENAME as additionalRole," +
                " SECTION_NAME as section " +
                "from V_USERS " +
                "WHERE   USERTYPE in (:userType) " +
                " AND PROVINCE_ID in (:provinces) " +
                " AND USERCREATIONDATE between :fromDate and :toDate";

        return jdbcTemplate
                .query(sql, parameters, new UserSummaryProjectionRowMapper());
    }

    private Resource generateCsvResource(List<UserSummaryProjection> userSummaryProjections) {
        try {


            final File userSummaryReport = File.createTempFile("UserSummaryReport", ".csv", null);
            final Writer writer = new FileWriter(userSummaryReport);

            StatefulBeanToCsv<UserSummaryProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<UserSummaryProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(userSummaryProjections);
            writer.close();

            return new FileSystemResource(userSummaryReport);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }


}
