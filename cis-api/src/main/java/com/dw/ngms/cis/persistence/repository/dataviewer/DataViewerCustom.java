package com.dw.ngms.cis.persistence.repository.dataviewer;

import com.dw.ngms.cis.persistence.projection.dataviewer.CustomQueryResultSetProjection;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
public interface DataViewerCustom {

    Collection<String> getAllColumnForTable(final String tableName);

    CustomQueryResultSetProjection getQueryResult(String query, String tableName);

}
