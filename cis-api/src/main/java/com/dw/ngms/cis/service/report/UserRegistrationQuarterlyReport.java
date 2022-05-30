package com.dw.ngms.cis.service.report;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.projection.report.UserRegistrationQuarterlyProjection;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.rowmapper.UserRegistrationQuarterlyRowMapper;
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
public class UserRegistrationQuarterlyReport implements GenericReport{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SpringTemplateEngine templateEngine;

    private final ReportUtils reportUtils;

    private final ProvinceRepository provinceRepository;

    @Autowired
    public UserRegistrationQuarterlyReport(NamedParameterJdbcTemplate jdbcTemplate,
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
            final String htmlReport = generateUserRegistrationQuarterlyHtmlReport(reportingVm);
            return reportUtils.generatePdfOfReport(htmlReport, "UserRegistrationQuarterly");
        } else if ("CSV".equalsIgnoreCase(reportingVm.getReportFormat())) {
            final List<UserRegistrationQuarterlyProjection> userRegistrationQuarterlyProjection = generateReportData(reportingVm);
            return generateCsvResource(userRegistrationQuarterlyProjection);
        }

        return null;
    }

    private String generateUserRegistrationQuarterlyHtmlReport(final ReportingVm reportingVm) {

        final Collection<UserRegistrationQuarterlyProjection> userRegistrationQuarterlyProjection = generateReportData(reportingVm);
        reportingVm.setProvinceName(reportingVm.getProvinces().stream().map(aLong -> {
            final Province byProvinceId = provinceRepository.findByProvinceId(aLong);
            if (null != byProvinceId)
                return byProvinceId.getProvinceName();
            return null;
        }).collect(Collectors.toSet()));

        final List<String> columnName = reportUtils.getColumnName(UserRegistrationQuarterlyProjection.class);


        Context context = new Context();
        context.setVariable(DATA, userRegistrationQuarterlyProjection);
        context.setVariable(REQUEST, reportingVm);
        context.setVariable(COLUMNS, columnName);
        return templateEngine.process("user-registration-quarterly.html", context);
    }

    private List<UserRegistrationQuarterlyProjection> generateReportData(final ReportingVm reportingVm) {
        Map<String, Object> map = new HashMap();
        map.put("userType", reportingVm.getUserType());
        map.put("provinces", reportingVm.getProvinces());
        map.put("fromDate", reportingVm.getFromDate());
        map.put("toDate", reportingVm.getToDate());
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        String sql = "SELECT Province_Name as provinceName," +
                "UserType as userType," +
                "YEAR as year," +
                "QUARTER as quarter," +
                "TOTAL_USER_REGISTRATION as totalUserRegistration," +
                "REGISTRATIONDATE as registrationDate " +
                "from V_USER_REGISTRATION_QUATERLY " +
                "WHERE USERTYPE in (:userType) " +
                " AND PROVINCE_ID in (:provinces) " +
                " AND REGISTRATIONDATE between :fromDate and :toDate";
        return jdbcTemplate
                .query(sql, parameters, new UserRegistrationQuarterlyRowMapper());
    }

    private Resource generateCsvResource(List<UserRegistrationQuarterlyProjection> userRegistrationQuarterlyProjection) {
        try {


            final File userRegistrationQuarterlyReport = File.createTempFile("userRegistrationQuarterlyReport", ".csv", null);
            final Writer writer = new FileWriter(userRegistrationQuarterlyReport);

            StatefulBeanToCsv<UserRegistrationQuarterlyProjection> beanToCsv =
                    new StatefulBeanToCsvBuilder<UserRegistrationQuarterlyProjection>(writer)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(',')
                            .build();

            beanToCsv.write(userRegistrationQuarterlyProjection);
            writer.close();

            return new FileSystemResource(userRegistrationQuarterlyReport);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV", ex);
            ex.printStackTrace();
        }

        return null;
    }
}
