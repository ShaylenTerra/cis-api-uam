package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.ManagerNotificationProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : pragayanshu
 * @since : 2021/06/19, Sat
 **/
public class ManagerNotificationProjectionRowMapper implements RowMapper<ManagerNotificationProjection> {

    @Override
    public ManagerNotificationProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        ManagerNotificationProjection managerNotificationProjection = new ManagerNotificationProjection();
        managerNotificationProjection.setReferenceNumber(rs.getString("REFERENCENUMBER"));
        managerNotificationProjection.setSubReferenceNumber(rs.getString("SUBREFERENCENUMBER"));
        managerNotificationProjection.setDateReceived(rs.getTimestamp("DATERECEIVED"));
        managerNotificationProjection.setProductCategory(rs.getString("PRODUCTCATEGORY"));
        managerNotificationProjection.setDateAllocated(rs.getTimestamp("DATEALLOCATED"));
        managerNotificationProjection.setUserName(rs.getString("USERNAME"));
        managerNotificationProjection.setUserRole(rs.getString("USERROLE"));
        managerNotificationProjection.setDateManagerNotified(rs.getTimestamp("DATEMANAGERNOTIFIED"));
        managerNotificationProjection.setNotificationType(rs.getString("NOTIFICATIONTYPE"));
        managerNotificationProjection.setUserComments(rs.getString("USERCOMMENTS"));
        managerNotificationProjection.setStatus(rs.getString("STATUS"));
        return managerNotificationProjection;
    }
}
