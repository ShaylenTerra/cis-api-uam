package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 03-01-2022
 */
public abstract class ReservationDraftDecorator implements ReservationDraftMapper {

    @Autowired
    @Qualifier("delegate")
    private ReservationDraftMapper delegate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public ReservationDraftDto reservationDraftToReservationDraftDto(ReservationDraft reservationDraft) {
        ReservationDraftDto reservationDraftDto = delegate.reservationDraftToReservationDraftDto(reservationDraft);
        if (null != reservationDraftDto) {
            Long userId = reservationDraftDto.getUserId();
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

                reservationDraftDto.setUserName(userName);
            }

            Province byProvinceId = provinceRepository.findByProvinceId(reservationDraftDto.getProvinceId());
            if(null != byProvinceId) {
                reservationDraftDto.setProvinceName(byProvinceId.getProvinceName());
            }
        }
        return reservationDraftDto;
    }
}
