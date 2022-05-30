package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.projection.report.RequestStatisticalProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : pragayanshu
 * @since : 2021/06/19, Sat
 **/
public class RequestStatisticalProjectionRowMapper implements RowMapper<RequestStatisticalProjection> {
    @Override
    public RequestStatisticalProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        RequestStatisticalProjection requestStatisticalProjection = new RequestStatisticalProjection();
        requestStatisticalProjection.setItem(rs.getString("ITEM"));
        requestStatisticalProjection.setDetails(rs.getString("DETAILS"));
        requestStatisticalProjection.setProvinceName(rs.getString("PROVINCENAME"));
        requestStatisticalProjection.setNumberOfRequest(rs.getLong("NUMBEROFREQUEST"));
        return requestStatisticalProjection;
    }
}
