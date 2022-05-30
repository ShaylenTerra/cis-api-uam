package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nirmal on 2020/11/06.
 */
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Province findByProvinceId(final Long provinceId);


}
