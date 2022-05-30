package com.dw.ngms.cis.persistence.repository.dataviewer;

import com.dw.ngms.cis.enums.DataViewerRequestStatus;
import com.dw.ngms.cis.persistence.domains.dataviewer.DataViewerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@Repository
public interface DataViewerRequestRepository extends JpaRepository<DataViewerRequest, Long> {

    Page<DataViewerRequest> findAllByUserid(final Long userId, final Pageable pageable);

    Collection<DataViewerRequest> findAllByProcess(DataViewerRequestStatus dataViewerRequestStatus);

    Collection<DataViewerRequest> findAllByProcessAndId(DataViewerRequestStatus dataViewerRequestStatus, final Long id);

}
