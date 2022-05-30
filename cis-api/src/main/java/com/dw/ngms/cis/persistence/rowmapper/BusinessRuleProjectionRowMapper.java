package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.BusinessRuleProjection;
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
        businessRuleProjection.setProcessName(rs.getString("PROCESSNAME"));
        businessRuleProjection.setModuleName(rs.getString("MODULENAME"));
        businessRuleProjection.setContext(rs.getString("CONTEXT"));
        businessRuleProjection.setUserName(rs.getString("USERNAME"));
        businessRuleProjection.setReferenceNumberSample(rs.getString("REFERENCENUMBERSAMPLE"));
        businessRuleProjection.setNotificationTime(rs.getLong("NOTIFICATIONTIME"));
        businessRuleProjection.setTurnaroundTime(rs.getLong("TURNAROUNDTIME"));
        businessRuleProjection.setDated(rs.getTimestamp("DATED"));
        return businessRuleProjection;
    }
}
