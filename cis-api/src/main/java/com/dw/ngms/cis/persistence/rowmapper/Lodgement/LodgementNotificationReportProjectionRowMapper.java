package com.dw.ngms.cis.persistence.rowmapper.Lodgement;

import com.dw.ngms.cis.persistence.projection.report.Lodgement.LodgementNotificationReportProjection;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LodgementNotificationReportProjectionRowMapper  implements RowMapper<LodgementNotificationReportProjection> {
/**
 * @author : AnkitSaxena
 * @since : 11/04/22, Mon
 **/
    @Override
    public LodgementNotificationReportProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        LodgementNotificationReportProjection Projection = new LodgementNotificationReportProjection();
        Projection.setReferenceNumber(rs.getString("REFERENCENUMBER"));
        Projection.setSubReferenceNumber(rs.getString("SUBREFERENCENUMBER"));
        Projection.setDateReceived(rs.getTimestamp("DATERECEIVED"));
        Projection.setPropertyDescription(rs.getString("PROPERTYDESCRIPTION"));
        Projection.setDateAllocated(rs.getTimestamp("DATEALLOCATED"));
        Projection.setUserName(rs.getString("USERNAME"));
        Projection.setUserRole(rs.getString("USERROLE"));
        Projection.setDateOfficerNotified(rs.getTimestamp("DATEOFFICERNOTIFIED"));
        Projection.setNotificationType(rs.getString("notificationType"));
        Projection.setUserComments(rs.getString("USERCOMMENTS"));
        Projection.setStatus(rs.getString("Status"));


        return Projection;
    }

}

