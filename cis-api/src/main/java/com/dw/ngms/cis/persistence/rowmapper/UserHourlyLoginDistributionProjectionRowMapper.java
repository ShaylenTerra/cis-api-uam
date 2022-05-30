package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.UserHourlyLoginDistributionProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : prateekgoel
 * @since : 05/05/21, Wed
 **/
public class UserHourlyLoginDistributionProjectionRowMapper implements RowMapper<UserHourlyLoginDistributionProjection> {


    @Override
    public UserHourlyLoginDistributionProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserHourlyLoginDistributionProjection userHourlyLoginDistributionProjection
                = new UserHourlyLoginDistributionProjection();
        userHourlyLoginDistributionProjection.setHours(rs.getString("HOURS"));
        userHourlyLoginDistributionProjection.setTotalUsers(rs.getLong("TOTALUSER"));
        return userHourlyLoginDistributionProjection;
    }
}
