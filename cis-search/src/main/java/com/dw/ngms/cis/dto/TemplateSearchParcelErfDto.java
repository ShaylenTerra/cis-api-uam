package com.dw.ngms.cis.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 22/02/21, Mon
 **/
@Data
public class TemplateSearchParcelErfDto {

    @CsvBindByName(column = "ERF_NUMBER")
    private String erfNumber;

    @CsvBindByName(column = "PORTION")
    private String portion;

    @CsvBindByName(column = "RESULT")
    private String result;
}
