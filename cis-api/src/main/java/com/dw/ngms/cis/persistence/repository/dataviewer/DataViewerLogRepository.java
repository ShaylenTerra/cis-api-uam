package com.dw.ngms.cis.persistence.repository.dataviewer;

import com.dw.ngms.cis.persistence.domains.dataviewer.DataViewerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@Repository
public interface DataViewerLogRepository extends JpaRepository<DataViewerLog, Long> {
}
