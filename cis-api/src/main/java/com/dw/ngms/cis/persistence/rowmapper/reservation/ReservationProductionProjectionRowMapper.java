package com.dw.ngms.cis.persistence.rowmapper.reservation;

import com.dw.ngms.cis.persistence.projection.report.reservation.ReservationProductionProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : pragayanshu
 * @since : 2021/06/18, Fri
 **/
public class ReservationProductionProjectionRowMapper implements RowMapper<ReservationProductionProjection> {

    @Override
    public ReservationProductionProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReservationProductionProjection reservationProductionProjection = new ReservationProductionProjection();
        reservationProductionProjection.setReferenceNumber(rs.getString("REFERENCENUMBER"));
        reservationProductionProjection.setPropertyDescription(rs.getString("PROPERTYDESCRIPTION"));
        reservationProductionProjection.setNoOfParentDesignation(rs.getString("NOOFPARENTDESIGNATION"));
        reservationProductionProjection.setReservationType(rs.getString("RESERVATIONTYPE"));
        reservationProductionProjection.setNoOfRequestedDesignation(rs.getString("NOOFREQUESTEDDESIGNATION"));
        reservationProductionProjection.setDateReceived(rs.getTimestamp("DATERECEIVED"));
        reservationProductionProjection.setApplicantFullName(rs.getString("APPLICANTFULLNAME"));
        reservationProductionProjection.setApplicantRole(rs.getString("APPLICANTROLE"));
        reservationProductionProjection.setOfficerName(rs.getString("officerName"));
        reservationProductionProjection.setOfficerReceived(rs.getTimestamp("officerRecived"));
        reservationProductionProjection.setOfficerTimeTaken(rs.getLong("officerTimeTaken"));
        reservationProductionProjection.setScrutnizerName(rs.getString("scrutnizerName"));
        reservationProductionProjection.setScrutinizerReceived(rs.getTimestamp("scrutnizerReceived"));
        reservationProductionProjection.setScrutnizerTimeTaken(rs.getLong("scrutnizerTimeTaken"));
        reservationProductionProjection.setDateReserved(rs.getTimestamp("ReservationDate"));
        reservationProductionProjection.setTaskTotalProductivity(rs.getLong("taskTotalProductivity"));
        reservationProductionProjection.setTotalProductivity(rs.getLong("totalProductivity"));
        reservationProductionProjection.setApplicantType(rs.getString("applicantType"));
        return reservationProductionProjection;
    }
}
