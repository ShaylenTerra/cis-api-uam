package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 03-01-2022
 */
public abstract class LodgementDraftDecorator implements LodgementDraftMapper {

    @Autowired
    @Qualifier("delegate")
    private LodgementDraftMapper delegate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public LodgementDraftDto lodgementDraftToLodgementDraftDto(LodgementDraft lodgementDraft) {
        LodgementDraftDto lodgementDraftDto = delegate.lodgementDraftToLodgementDraftDto(lodgementDraft);
        if (null != lodgementDraftDto) {
            Long userId = lodgementDraftDto.getUserId();
            User userByUserId = userRepository.findUserByUserId(userId);
            String userName = "";
            if(null != userByUserId) {

                String firstName = userByUserId.getFirstName();
                if (StringUtils.isNotBlank(firstName)) {
                    userName += firstName;
                }
                String surname = userByUserId.getSurname();
                if (StringUtils.isNotBlank(surname)) {
                    userName += " "+surname;
                }

                lodgementDraftDto.setUserName(userName);
            }

            Province byProvinceId = provinceRepository.findByProvinceId(lodgementDraftDto.getProvinceId());
            if(null != byProvinceId) {
                lodgementDraftDto.setProvinceName(byProvinceId.getProvinceName());
            }
        }
        return lodgementDraftDto;
    }
}
