package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.UserProductionProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : pragayanshu
 * @since : 2021/06/19, Sat
 **/
public class UserProductionProjectionRowMapper implements RowMapper<UserProductionProjection> {
    @Override
    public UserProductionProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserProductionProjection userProductionProjection = new UserProductionProjection();
        userProductionProjection.setReferenceNumber(rs.getString("REFERENCENUMBER"));
        userProductionProjection.setDateReceived(rs.getTimestamp("DATERECEIVED"));
        userProductionProjection.setProductCategory(rs.getString("PRODUCTCATEGORY"));
        userProductionProjection.setProductItem(rs.getString("PRODUCTITEM"));
        userProductionProjection.setProductQuantity(rs.getLong("PRODUCTQUANTITY"));
        userProductionProjection.setRequester(rs.getString("REQUESTER"));
        userProductionProjection.setRequesterType(rs.getString("REQUESTERTYPE"));
        userProductionProjection.setRequesterRole(rs.getString("REQUESTERROLE"));
        userProductionProjection.setRequesterSector(rs.getString("REQUESTERSECTOR"));
        userProductionProjection.setDateAllocated(rs.getTimestamp("DATEALLOCATED"));
        userProductionProjection.setOfficer(rs.getString("OFFICER"));
        userProductionProjection.setDateCompleted(rs.getTimestamp("DATECOMPLETED"));
        userProductionProjection.setActionContext(rs.getString("ACTIONCONTEXT"));
        userProductionProjection.setProductivityMinutes(rs.getLong("PRODUCTIVITYMINUTES"));
        userProductionProjection.setCost(rs.getLong("COST"));
        userProductionProjection.setInvoiceNumber(rs.getString("INVOICENUMBER"));
        userProductionProjection.setStatus(rs.getString("STATUS"));
        return userProductionProjection;
    }
}
