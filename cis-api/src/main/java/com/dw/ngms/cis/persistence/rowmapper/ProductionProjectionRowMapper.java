package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.ProductionProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : pragayanshu
 * @since : 2021/06/18, Fri
 **/
public class ProductionProjectionRowMapper implements RowMapper<ProductionProjection> {

    @Override
    public ProductionProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductionProjection productionProjection = new ProductionProjection();
        productionProjection.setReferenceNumber(rs.getString("REFERENCENUMBER"));
        productionProjection.setDateReceived(rs.getTimestamp("DATERECEIVED"));
        productionProjection.setProductCategory(rs.getString("PRODUCTCATEGORY"));
        productionProjection.setRequester(rs.getString("REQUESTER"));
        productionProjection.setRequesterType(rs.getString("REQUESTERTYPE"));
        productionProjection.setRequesterRole(rs.getString("REQUESTERROLE"));
        productionProjection.setRequesterSector(rs.getString("REQUESTERSECTOR"));
        productionProjection.setDateReceivedOffice(rs.getTimestamp("DATERECEIVEDOFFICE"));
        productionProjection.setOfficer(rs.getString("OFFICER"));
        productionProjection.setOfficerProductivity(rs.getLong("OFFICERPRODUCTIVITY"));
        productionProjection.setDateReceivedScrutinized(rs.getString("DATERECEIVEDSCRUTINIZED"));
        productionProjection.setScrutinizerName(rs.getString("SCRUTINIZERNAME"));
        productionProjection.setScrutinizerProductivity(rs.getLong("SCRUTINIZERPRODUCTIVITY"));
        productionProjection.setTurnaroundTime(rs.getLong("TURNAROUNDTIME"));
        productionProjection.setProductivityMinutes(rs.getLong("PRODUCTIVITYMINUTES"));
        productionProjection.setCost(rs.getLong("COST"));
        productionProjection.setInvoiceNumber(rs.getString("INVOICENUMBER"));
        productionProjection.setStatus(rs.getString("STATUS"));
        return productionProjection;
    }
}
