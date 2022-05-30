package com.dw.ngms.cis.persistence.projection;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 20/06/21, Sun
 **/
public interface SearchProfileRelatedData {

    String getDocumentType();

    String getSgNo();

    String getDocumentNo();

    String getDocumentSubType();

    String getDescription();

    String getRecordId();

}
