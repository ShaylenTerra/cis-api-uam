package com.dw.ngms.cis.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 22/02/21, Mon
 **/
@Data
public class TemplateSearchSurveyRecordNumberDto {

    @CsvBindByName(column = "SURVEY_RECORD_NUMBER")
    private String surveyRecordNumber;

    @CsvBindByName(column = "RESULT")
    private String result;

}
