package com.dw.ngms.cis.persistence.rowmapper.reservation;

import com.dw.ngms.cis.persistence.projection.report.reservation.ReservationStatusProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : pragayanshu
 * @since : 2021/06/18, Fri
 **/
public class ReservationStatusProjectionRowMapper implements RowMapper<ReservationStatusProjection> {

    @Override
    public ReservationStatusProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReservationStatusProjection reservationStatusProjection = new ReservationStatusProjection();
        reservationStatusProjection.setReferenceNumber(rs.getString("REFERENCENUMBER"));
        reservationStatusProjection.setPropertyDescription(rs.getString("PROPERTYDESCRIPTION"));
        reservationStatusProjection.setNoOfParentDesignation(rs.getString("NOOFPARENTDESIGNATION"));
        reservationStatusProjection.setReservationType(rs.getString("RESERVATIONTYPE"));
        reservationStatusProjection.setNoOfRequestedDesignation(rs.getString("NOOFREQUESTEDDESIGNATION"));
        reservationStatusProjection.setDateReceived(rs.getTimestamp("DATERECEIVED"));
        reservationStatusProjection.setApplicantFullName(rs.getString("APPLICANTFULLNAME"));
        reservationStatusProjection.setApplicantRole(rs.getString("APPLICANTROLE"));
        reservationStatusProjection.setOfficerName(rs.getString("officerName"));
        reservationStatusProjection.setOfficerReceived(rs.getTimestamp("officerRecived"));
        reservationStatusProjection.setOfficerTimeTaken(rs.getLong("officerTimeTaken"));
        reservationStatusProjection.setManagerName(rs.getString("managerName"));
        reservationStatusProjection.setManagerReceived(rs.getTimestamp("managerReceived"));
        reservationStatusProjection.setTaskStatus(rs.getString("taskInternalStatusCaption"));
        reservationStatusProjection.setReservationStatus(rs.getString("reservationStatus"));
        reservationStatusProjection.setApplicantType(rs.getString("applicantType"));
        return reservationStatusProjection;
    }
}
