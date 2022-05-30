package com.dw.ngms.cis.persistence.repository.dataviewer;

import com.dw.ngms.cis.persistence.projection.dataviewer.CustomQueryResultSetProjection;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
public class DataViewerCustomImpl implements DataViewerCustom {


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Collection<String> getAllColumnForTable(String tableName) {

        return entityManager.createNativeQuery("SELECT  column_name as coulnName\n" +
                "FROM USER_TAB_COLUMNS\n" +
                "WHERE table_name = :tableName")
                .setParameter("tableName", tableName)
                .getResultList();


    }

    @Override
    public CustomQueryResultSetProjection getQueryResult(String query, String tableName) {
        CustomQueryResultSetProjection customQueryResultSetProjection = new CustomQueryResultSetProjection();
        Optional first = entityManager.createNativeQuery("Select count(*) from " + tableName).getResultList()
                .stream()
                .findFirst();
        if (first.isPresent()) {
            customQueryResultSetProjection.setTotalCount(first.get());
        }

        final List resultList = entityManager.createNativeQuery(query).unwrap(Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .getResultList();

        customQueryResultSetProjection.setData(resultList);
        return customQueryResultSetProjection;
    }
}
