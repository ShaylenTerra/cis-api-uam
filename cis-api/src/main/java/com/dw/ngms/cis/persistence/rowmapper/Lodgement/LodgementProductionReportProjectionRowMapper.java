package com.dw.ngms.cis.persistence.rowmapper.Lodgement;


import com.dw.ngms.cis.persistence.projection.report.Lodgement.LodgementProductionReportProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LodgementProductionReportProjectionRowMapper implements RowMapper<LodgementProductionReportProjection> {
    /**
     * @author : AnkitSaxena
     * @since : 11/04/22, Mon
     **/
    @Override
    public LodgementProductionReportProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        LodgementProductionReportProjection Projection = new LodgementProductionReportProjection();
        Projection.setLodgementReference(rs.getString("LodgementReference"));
        Projection.setProfessionalpractitioner(rs.getString("Professionalpractitioner"));
        Projection.setDateSubmitted(rs.getTimestamp("DateSubmitted"));
        Projection.setBatch(rs.getString("Batch"));
        Projection.setSG(rs.getString("SG"));
        Projection.setSRNumber(rs.getString("SRNumber"));
        Projection.setLodgementType(rs.getString("LodgementType"));
        Projection.setLodgementSubType(rs.getString("LodgementSubType"));
        Projection.setParcelDescription(rs.getString("ParcelDescription"));
        Projection.setErvens(rs.getString("Ervens"));
        Projection.setDateLodged(rs.getTimestamp("DateLodged"));
        Projection.setTurnAroundTime(rs.getString("TurnAroundTime"));
        Projection.setOfficer(rs.getString("Officer"));
        Projection.setScrutinizer(rs.getString("Scrutinizer"));
        Projection.setStatus(rs.getString("Status"));
        Projection.setDateVerified(rs.getTimestamp("DateVerified"));
        Projection.setAmountPaid(rs.getString("AmountPaid"));
        Projection.setPaymentMethod(rs.getString("PaymentMethod"));
        Projection.setReceiptNumber(rs.getString("ReceiptNumber"));
        return Projection;
    }

}

