package com.dw.ngms.cis.persistence.rowmapper.reservation;

import com.dw.ngms.cis.persistence.projection.report.reservation.ManagerNotificationProjection;
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
        managerNotificationProjection.setReferenceNumber(rs.getString("referenceNumber"));
        managerNotificationProjection.setSubReferenceNumber(rs.getString("subReferenceNumber"));
        managerNotificationProjection.setDateReceived(rs.getTimestamp("dateReceived"));
        managerNotificationProjection.setPropertyDescription(rs.getString("propertyDescription"));
        managerNotificationProjection.setDateAllocated(rs.getTimestamp("dateAllocated"));
        managerNotificationProjection.setUserName(rs.getString("userName"));
        managerNotificationProjection.setUserRole(rs.getString("userRole"));
        managerNotificationProjection.setDateOfficerNotified(rs.getTimestamp("dateOfficerNotified"));
        managerNotificationProjection.setNotificationType(rs.getString("notificationType"));
        managerNotificationProjection.setUserComments(rs.getString("userComments"));
        managerNotificationProjection.setStatus(rs.getString("status"));
        return managerNotificationProjection;
    }
}
