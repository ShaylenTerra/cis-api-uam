package com.dw.ngms.cis.persistence.projection.report;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 05/05/21, Wed
 **/
@Data
public class UserHourlyLoginDistributionProjection {

    @CsvBindByName(column = "Total Users")
    private Long totalUsers;

    @CsvBindByName(column = "hours")
    private String hours;
}
