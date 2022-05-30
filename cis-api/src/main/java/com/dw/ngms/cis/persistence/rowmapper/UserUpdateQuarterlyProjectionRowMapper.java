package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.UserUpdateQuarterlyProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : pragayanshu
 * @since : 2021/06/23, Wed
 **/
public class UserUpdateQuarterlyProjectionRowMapper implements RowMapper <UserUpdateQuarterlyProjection> {
    @Override
    public UserUpdateQuarterlyProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserUpdateQuarterlyProjection userUpdateQuarterlyProjection = new UserUpdateQuarterlyProjection();
        userUpdateQuarterlyProjection.setProvinceName(rs.getString("PROVINCENAME"));
        userUpdateQuarterlyProjection.setUserType(rs.getString("USERTYPE"));
        userUpdateQuarterlyProjection.setYear(rs.getString("YEAR"));
        userUpdateQuarterlyProjection.setQuarter(rs.getString("QUARTER"));
        userUpdateQuarterlyProjection.setTotalUserRegistration(rs.getLong("TOTALUSERREGISTRATION"));
        return userUpdateQuarterlyProjection;
    }
}
