package com.dw.ngms.cis.persistence.repository.listmanagement;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 06/12/20, Sun
 **/
@Repository
public interface ListMasterRepository extends JpaRepository<ListMaster, Long> {

}
