package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.UserSummaryProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author : prateekgoel
 * @since : 05/05/21, Wed
 **/
public class UserSummaryProjectionRowMapper implements RowMapper<UserSummaryProjection> {


    @Override
    public UserSummaryProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserSummaryProjection userSummaryProjection = new UserSummaryProjection();
        userSummaryProjection.setRole(rs.getString("ROLE"));
        userSummaryProjection.setProvince(rs.getString("PROVINCE"));
        userSummaryProjection.setLastLoginDate(rs.getTimestamp("LASTLOGINDATE"));
        userSummaryProjection.setStatus(rs.getString("STATUS"));
        userSummaryProjection.setUserType(rs.getString("USERTYPE"));
        userSummaryProjection.setUserCreationDate(rs.getTimestamp("USERCREATIONDATE"));
        userSummaryProjection.setFullName(rs.getString("FULLNAME"));
        userSummaryProjection.setAdditionalRole(rs.getString("ADDITIONALROLE"));
        userSummaryProjection.setSection(rs.getString("SECTION"));
        userSummaryProjection.setUserName(rs.getString("USERNAME"));
        userSummaryProjection.setOrganisation(rs.getString("ORGANISATION"));
        userSummaryProjection.setSector(rs.getString("SECTOR"));
        return userSummaryProjection;
    }
}
