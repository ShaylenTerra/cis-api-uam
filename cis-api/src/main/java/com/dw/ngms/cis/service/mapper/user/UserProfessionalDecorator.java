package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.domains.user.UserProfessional;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.service.dto.user.UserProfessionalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UserProfessionalDecorator implements UserProfessionalMapper {

    @Autowired
    @Qualifier("delegate")
    private UserProfessionalMapper delegate;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public UserProfessionalDto userProfessionalToUserProfessionalDTO(UserProfessional userProfessional) {

        UserProfessionalDto userProfessionalDto = delegate.userProfessionalToUserProfessionalDTO(userProfessional);

        Province byProvinceId = provinceRepository.findByProvinceId(userProfessionalDto.getProvinceId());

        if(null != byProvinceId)
            userProfessionalDto.setProvinceName(byProvinceId.getProvinceName());

        return userProfessionalDto;
    }
}
