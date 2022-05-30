package com.dw.ngms.cis.service;

import com.dw.ngms.cis.dto.TemplateSearchSgNumberDto;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
@Slf4j
public class CsvWriter<T> {

    public Resource generateCsvResource(List<T> data, Class<T> beanClass, final String fileName) {
        try {

            final File templateSearchSgNumberFile = File.createTempFile(fileName, ".csv", null);
            final Writer writer = new FileWriter(templateSearchSgNumberFile);

            StatefulBeanToCsv beanToCsv =
                    new StatefulBeanToCsvBuilder(writer)
                            .withOrderedResults(true)
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                            .build();

            beanToCsv.write(data);
            writer.close();

            return new FileSystemResource(templateSearchSgNumberFile);

        } catch (CsvException | IOException ex) {
            log.error("Error mapping Bean to CSV with cause [{}]", ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }

}
