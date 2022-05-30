package com.dw.ngms.cis.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 12/01/21, Tue
 **/
@Data
public class TemplateSearchSgNumberDto {

    @CsvBindByName(column = "SGNUMBER")
    private String sgNumber;

    @CsvBindByName(column = "RESULT")
    private String result;


}
