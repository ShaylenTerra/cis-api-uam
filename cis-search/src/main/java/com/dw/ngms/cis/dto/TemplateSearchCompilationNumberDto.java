package com.dw.ngms.cis.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 22/02/21, Mon
 **/
@Data
public class TemplateSearchCompilationNumberDto {

    @CsvBindByName(column = "COMPILATION_NUMBER")
    private String compilationNumber;

    @CsvBindByName(column = "RESULT")
    private String result;
}
