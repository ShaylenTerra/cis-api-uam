package com.dw.ngms.cis.service.report;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.UserUpdateQuarterlyProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.UserUpdateQuarterlyProjectionRowMapper;
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
public class UserUpdateQuarterlyReport implements GenericReport {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Autowired
    public UserUpdateQuarterlyReport(NamedParameterJdbcTemplate jdbcTemplate,
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
            final String htmlReport = generateUserUpdateQuarterlyHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "UserUpdatedQuarterly");
        } else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final List<UserUpdateQuarterlyProjection> userUpdateQuarterlyProjection = generateReportData(reportingVm);
            return generateCsvResource(userUpdateQuarterlyProjection);
        }

        return null;
    }

    private String generateUserUpdateQuarterlyHtmlReport(final ReportingVm reportingVm) {

        final Collection<UserUpdateQuarterlyProjection> userUpdateQuarterlyProjection = generateReportData(reportingVm);
        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(UserUpdateQuarterlyProjection.class);


        Context context = new Context();
        context.setVariable(DATA, userUpdateQuarterlyProjection);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("user-update-quarterly.html", context);
    }

    private List<UserUpdateQuarterlyProjection> generateReportData(final ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        String sql = "SELECT PROVINCE_NAME as provinceName," +
                "UserType as userType," +
                "YEAR as year," +
                "QUARTER as quarter," +
                "TOTAL_USER_REGISTRATION as totalUserRegistration " +
                "from V_USER_AUDIT_QUATERLY " +
                "WHERE USERTYPE in (:userType) " +
                " AND PROVINCE_ID in (:provinces) ";
        return jdbcTemplate
                .query(sql, parameters, new UserUpdateQuarterlyProjectionRowMapper());
    }

    private Resource generateCsvResource(List<UserUpdateQuarterlyProjection> userUpdateQuarterlyProjection) {
        try {


            final File userUpdateQuarterlyReport = File.createTempFile("userUpdateQuarterlyReport", ".csv", null);
            final Writer writer = new FileWriter(userUpdateQuarterlyReport);

            StatefulBeanToCsv<UserUpdateQuarterlyProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<UserUpdateQuarterlyProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(userUpdateQuarterlyProjection);
            writer.close();

            return new FileSystemResource(userUpdateQuarterlyReport);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }
}

