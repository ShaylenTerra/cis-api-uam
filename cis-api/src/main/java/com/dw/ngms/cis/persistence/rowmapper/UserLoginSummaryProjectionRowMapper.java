package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.UserLoginSummaryProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : prateekgoel
 * @since : 05/05/21, Wed
 **/
public class UserLoginSummaryProjectionRowMapper implements RowMapper<UserLoginSummaryProjection> {


    @Override
    public UserLoginSummaryProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserLoginSummaryProjection userLoginSummaryProjection = new UserLoginSummaryProjection();
        userLoginSummaryProjection.setLoginDate(rs.getTimestamp("LOGINDATE"));
        userLoginSummaryProjection.setTotalUser(rs.getLong("TOTALUSER"));
        userLoginSummaryProjection.setProvinceName(rs.getString("PROVINCENAME"));
        return userLoginSummaryProjection;
    }
}
