package com.dw.ngms.cis.persistence.projection.report;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : pragayanshu
 * @since : 2021/06/19, Sat
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatisticalProjection {

    @CsvBindByName(column = "Job Description/Category")
    @DisplayName("Job Description/Category")
    private String item;

    @CsvBindByName(column = "Item")
    @DisplayName("Item")
    private String details;

    @CsvBindByName(column = "Province Name")
    @DisplayName("Province Name")
    private String provinceName;

    @CsvBindByName(column = "Number Of Request")
    @DisplayName("Number Of Request")
    private Long numberOfRequest;

}
