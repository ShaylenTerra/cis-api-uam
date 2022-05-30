package com.dw.ngms.cis.persistence.repository.province;

import com.dw.ngms.cis.persistence.domains.province.ProvinceAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 26/01/21, Tue
 **/
@Repository
public interface ProvinceAddressRepository extends JpaRepository<ProvinceAddress, Long> {

    ProvinceAddress findByProvinceId(final Long provinceId);
}
