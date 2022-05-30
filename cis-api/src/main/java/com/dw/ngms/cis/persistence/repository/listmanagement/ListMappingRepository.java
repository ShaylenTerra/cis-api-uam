package com.dw.ngms.cis.persistence.repository.listmanagement;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ListMappingRepository extends JpaRepository<ListMapping,Long> {

}
