package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.SgdataDocuments;
import com.dw.ngms.cis.persistence.rowmapper.SgdataDocumentsRowMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.List;

/**
 * @author : prateekgoel
 * @since : 19/05/21, Wed
 **/
@AllArgsConstructor
@Slf4j
public class CustomSgdataDocumentRepositoryImpl implements CustomSgdataDocumentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Page<SgdataDocuments> findByRecordId(Long recordId, Pageable pageable) {

        String rowCountSql = "SELECT count(1) " +
                "FROM SGDATA_DOCUMENTS " +
                "WHERE RECORD_ID = ? ";

        int total = jdbcTemplate.queryForObject(rowCountSql, (rs, rowNum) -> rs.getInt(1), recordId);

        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("RECORD_ID");

        final String dataQuery = "SELECT * FROM SGDATA_DOCUMENTS WHERE RECORD_ID = ? ORDER BY " + order.getProperty() + " " + order.getDirection().name() +
                " OFFSET " + pageable.getOffset() + " ROWS FETCH NEXT " + pageable.getPageSize() + " ROWS ONLY";

        log.debug(" query to get records from SGDATA_DOCUMENTS [{}]", dataQuery);

        final List<SgdataDocuments> query = jdbcTemplate.query(dataQuery,
                new SgdataDocumentsRowMapper(),
                recordId);

        return new PageImpl<>(query, pageable, total);
    }

    @Override
    public Collection<SgdataDocuments> findByRecordId(Long recordId) {
        if(null != recordId) {

            final String dataQuery = "SELECT * FROM SGDATA_DOCUMENTS WHERE RECORD_ID = ?";

            return jdbcTemplate.query(dataQuery,
                    new SgdataDocumentsRowMapper(),
                    recordId);
        }
        return null;
    }
}
