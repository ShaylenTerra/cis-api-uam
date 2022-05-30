package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisworkflow.persistence.repository.UserAdditionalRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 26/05/21, Wed
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserAdditionalRoleService {

    private final UserAdditionalRoleRepository userAdditionalRoleRepository;

    /**
     * method to fetch userId based on provinceId and roleId
     *
     * @param provinceId provinceId
     * @param roleId roleId
     * @return userId userId
     */
    public Long getUserIdByProvinceIdAndRoleId(final Long provinceId, final Long roleId) {
        final Collection<Long> byProvinceIdAndRoleId = userAdditionalRoleRepository
                .findByProvinceIdAndRoleId(provinceId, roleId);
        return byProvinceIdAndRoleId.stream()
                .findFirst()
                .orElse(1522L);

    }
}
