package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.UserUpdateHistoryProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : pragayanshu
 * @since : 2021/06/23, Wed
 **/
public class UserUpdateHistoryProjectionRowMapper implements RowMapper<UserUpdateHistoryProjection> {

    @Override
    public UserUpdateHistoryProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserUpdateHistoryProjection userUpdateHistoryProjection = new UserUpdateHistoryProjection();
        userUpdateHistoryProjection.setRole(rs.getString("ROLE"));
        userUpdateHistoryProjection.setProvince(rs.getString("PROVINCE"));
        userUpdateHistoryProjection.setLastLoginDate(rs.getTimestamp("LASTLOGINDATE"));
        userUpdateHistoryProjection.setStatus(rs.getString("STATUS"));
        userUpdateHistoryProjection.setUserType(rs.getString("USERTYPE"));
        userUpdateHistoryProjection.setUserCreationDate(rs.getTimestamp("USERCREATIONDATE"));
        userUpdateHistoryProjection.setFullName(rs.getString("FULLNAME"));
        userUpdateHistoryProjection.setAdditionalRole(rs.getString("ADDITIONALROLE"));
        userUpdateHistoryProjection.setSection(rs.getString("SECTION"));
        userUpdateHistoryProjection.setUserName(rs.getString("USERNAME"));
        userUpdateHistoryProjection.setOrganisation(rs.getString("ORGANISATION"));
        userUpdateHistoryProjection.setSector(rs.getString("SECTOR"));
        return userUpdateHistoryProjection;
    }
}
