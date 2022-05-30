package com.dw.ngms.cis.persistence.repository.dataviewer;

import com.dw.ngms.cis.persistence.domains.dataviewer.DataViewerConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@Repository
public interface DataViewerRepository extends JpaRepository<DataViewerConfiguration, String>,
        DataViewerCustom {

    @Query("select distinct d.objectType from DataViewerConfiguration d")
    Collection<String> findDistinctObjectType();

    Collection<DataViewerConfiguration> findByObjectType(final String objectType);

}
