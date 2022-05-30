package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.UserRegistrationQuarterlyProjection;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : prateekgoel
 * @since : 05/05/21, Wed
 **/
public class UserRegistrationQuarterlyRowMapper implements RowMapper<UserRegistrationQuarterlyProjection> {

    @Override
    public UserRegistrationQuarterlyProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRegistrationQuarterlyProjection userRegistrationQuarterlyProjection =
                new UserRegistrationQuarterlyProjection();
        userRegistrationQuarterlyProjection.setProvinceName(rs.getString("PROVINCENAME"));
        userRegistrationQuarterlyProjection.setUserType(rs.getString("USERTYPE"));
        userRegistrationQuarterlyProjection.setYear(rs.getString("YEAR"));
        userRegistrationQuarterlyProjection.setQuarter(rs.getString("QUARTER"));
        userRegistrationQuarterlyProjection.setTotalUserRegistration(rs.getLong("TOTALUSERREGISTRATION"));
        userRegistrationQuarterlyProjection.setRegistrationDate(rs.getTimestamp("REGISTRATIONDATE"));
        return userRegistrationQuarterlyProjection;
    }
}
