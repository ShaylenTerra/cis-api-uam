package com.dw.ngms.cis.utilities;

import com.dw.ngms.cis.persistence.projection.report.DisplayName;
import com.dw.ngms.cis.persistence.projection.report.UserSummaryProjection;
import com.dw.ngms.cis.service.report.GenericReport;
import com.dw.ngms.cis.service.workflow.PdfGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 15/06/21, Tue
 **/
@Component
@Slf4j
public class ReportUtils {

    private final PdfGeneratorService pdfGeneratorService;

    @Autowired
    public ReportUtils(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    public Resource generatePdfOfReport(final String processedHtml, final String reportName) {
        try {

            String tmpdir = Files.createTempFile(reportName, ".pdf").toString();

            String xhtml = TemplateUtils.convertToXhtml(processedHtml);

            String generatedFilePath = pdfGeneratorService.generatePdfFile(xhtml, tmpdir);

            return new FileSystemResource(generatedFilePath);
        } catch (IOException e) {
            log.error(" error while generating user summary report with cause {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getColumnName(Class<?> classz) {
        return Arrays.stream(classz.getDeclaredFields()).map(field -> {
            field.setAccessible(Boolean.TRUE);
            final DisplayName annotation = field.getAnnotation(DisplayName.class);
            if (null != annotation) {
                return annotation.value();
            }
            return field.getName();
        }).collect(Collectors.toList());
    }
}
