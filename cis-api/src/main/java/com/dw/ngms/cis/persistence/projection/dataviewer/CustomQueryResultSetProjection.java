package com.dw.ngms.cis.persistence.projection.dataviewer;

import lombok.Data;

import java.util.Collection;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@Data
public class CustomQueryResultSetProjection {

    private Collection<Map<String, Object>> data;

    private Object totalCount;
}
