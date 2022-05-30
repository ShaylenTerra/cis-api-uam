package com.dw.ngms.cis.cisworkflow.persistence.repository;

import com.dw.ngms.cis.cisworkflow.persistence.domain.MProvinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Repository
public interface ProvinceRepository extends JpaRepository<MProvinces, Long> {


}
