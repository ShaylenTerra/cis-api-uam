package com.dw.ngms.cis.persistence.rowmapper;

import com.dw.ngms.cis.persistence.domain.SgdataDocuments;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : prateekgoel
 * @since : 19/05/21, Wed
 **/
public class SgdataDocumentsRowMapper implements RowMapper<SgdataDocuments> {

    @Override
    public SgdataDocuments mapRow(ResultSet rs, int rowNum) throws SQLException {
        SgdataDocuments sgdataDocuments = new SgdataDocuments();
        sgdataDocuments.setDocumentId(rs.getLong("DOCUMENT_ID"));
        sgdataDocuments.setSgno(rs.getString("SGNO"));
        sgdataDocuments.setTitle(rs.getString("TITLE"));
        sgdataDocuments.setScandate(rs.getTimestamp("SCANDATE").toLocalDateTime());
        sgdataDocuments.setPageno(rs.getLong("PAGENO"));
        sgdataDocuments.setFileSize(rs.getLong("FILE_SIZE"));
        sgdataDocuments.setUrl(rs.getString("URL"));
        sgdataDocuments.setPreview(rs.getString("PREVIEW"));
        sgdataDocuments.setDocumentno(rs.getString("DOCUMENTNO"));
        sgdataDocuments.setThumbnail(rs.getString("THUMBNAIL"));
        sgdataDocuments.setRecordId(rs.getLong("RECORD_ID"));
        return sgdataDocuments;
    }
}
