package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.SearchSingleRequestDownload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 30/12/20, Wed
 **/
@Repository
public interface SearchSingleRequestDownloadRepository extends JpaRepository<SearchSingleRequestDownload, Long> {
}
