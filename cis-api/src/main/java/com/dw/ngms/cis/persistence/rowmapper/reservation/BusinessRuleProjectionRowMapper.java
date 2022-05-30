package com.dw.ngms.cis.persistence.rowmapper.reservation;

import com.dw.ngms.cis.persistence.projection.report.reservation.BusinessRuleProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : pragayanshu
 * @since : 2021/06/18, Fri
 **/
public class BusinessRuleProjectionRowMapper implements RowMapper<BusinessRuleProjection> {

    @Override
    public BusinessRuleProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        BusinessRuleProjection businessRuleProjection = new BusinessRuleProjection();
        businessRuleProjection.setProvince(rs.getString("PROVINCE"));
        businessRuleProjection.setModuleName(rs.getString("MODULENAME"));
        businessRuleProjection.setUserName(rs.getString("USERNAME"));
        businessRuleProjection.setBusinessRuleDetails(rs.getString("businessRuleDetails"));
        businessRuleProjection.setDated(rs.getTimestamp("DATED"));
        return businessRuleProjection;
    }
}
