package com.dw.ngms.cis.persistence.rowmapper.Lodgement;

import com.dw.ngms.cis.persistence.projection.report.Lodgement.LodgementBusinessReportProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LodgementBusinessRuleProjectionRowMapper implements RowMapper<LodgementBusinessReportProjection> {
/**
 * @author : AnkitSaxena
 * @since : 11/04/22, Mon
 **/
    @Override
    public LodgementBusinessReportProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        LodgementBusinessReportProjection Projection = new LodgementBusinessReportProjection();
        Projection.setProvinceName(rs.getString("ProvinceName"));
        Projection.setProcessName(rs.getString("ProcessName"));
        Projection.setModuleName(rs.getString("ModuleName"));
        Projection.setDated(rs.getTimestamp("Dated"));
        Projection.setUserName(rs.getString("userName"));
        Projection.setContext(rs.getString("context"));
        Projection.setBusinessRuleDetails(rs.getString("BusinessRuleDetails"));
        return Projection;
    }
}

