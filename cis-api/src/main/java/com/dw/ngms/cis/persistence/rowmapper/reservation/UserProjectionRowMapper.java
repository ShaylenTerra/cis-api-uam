package com.dw.ngms.cis.persistence.rowmapper.reservation;

import com.dw.ngms.cis.persistence.projection.report.reservation.UserReportProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : pragayanshu
 * @since : 2021/06/18, Fri
 **/
public class UserProjectionRowMapper implements RowMapper<UserReportProjection> {

    @Override
    public UserReportProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserReportProjection userReportProjection = new UserReportProjection();
        userReportProjection.setReferenceNumber(rs.getString("REFERENCENUMBER"));
        userReportProjection.setPropertyDescription(rs.getString("PROPERTYDESCRIPTION"));
        userReportProjection.setNoOfParentDesignation(rs.getString("NOOFPARENTDESIGNATION"));
        userReportProjection.setReservationType(rs.getString("RESERVATIONTYPE"));
        userReportProjection.setNoOfRequestedDesignation(rs.getString("NOOFREQUESTEDDESIGNATION"));
        userReportProjection.setDateReceived(rs.getTimestamp("DATERECEIVED"));
        userReportProjection.setApplicantFullName(rs.getString("APPLICANTFULLNAME"));
        userReportProjection.setApplicantRole(rs.getString("APPLICANTROLE"));
        userReportProjection.setOfficerName(rs.getString("officerName"));
        userReportProjection.setOfficerReceived(rs.getTimestamp("officerRecived"));
        userReportProjection.setOfficerTimeTaken(rs.getLong("officerTimeTaken"));
        userReportProjection.setApplicantType(rs.getString("applicantType"));
        userReportProjection.setTaskStatus(rs.getString("taskInternalStatusCaption"));
        userReportProjection.setTotalProductivity(rs.getLong("totalProductivity"));
        return userReportProjection;
    }
}
