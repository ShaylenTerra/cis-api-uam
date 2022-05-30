package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.OfficeTimingType;
import com.dw.ngms.cis.persistence.domains.OfficeTimings;
import com.dw.ngms.cis.persistence.repository.OfficeTimingsRepository;
import com.dw.ngms.cis.service.dto.OfficeTimeDto;
import com.dw.ngms.cis.service.mapper.OfficeTimeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by nirmal on 2020/11/18.
 */
@Service
@AllArgsConstructor
@Slf4j
public class OfficeTimingsService {


    private final OfficeTimingsRepository officeTimingsRepository;

    private final OfficeTimeMapper officeTimeMapper;

    private final Long IS_ACTIVE = 1L;

    /**
     * list all office timing based on userId and provinceId
     *
     * @return Page<OfficeTimeDto>
     */
    public Page<OfficeTimeDto> listOfficeTimings(final Long provinceId,
                                                 final OfficeTimingType officeTimingType,
                                                 final Pageable pageable) {
        return officeTimingsRepository.findByProvinceIdAndIsActiveAndOfficeTimingType(provinceId, IS_ACTIVE, officeTimingType, pageable)
                .map(officeTimeMapper::officeTimeToOfficeTimeDto);


    }


    /**
     * this method help to save officeTimings to db
     *
     * @param officeTimeDto {@link OfficeTimeDto}
     * @return {@link OfficeTimeDto}
     */
    public OfficeTimeDto addOfficeTime(final OfficeTimeDto officeTimeDto) {
        OfficeTimings officeTimings = officeTimeMapper.officeTimingDtoToOfficeTiming(officeTimeDto);
        officeTimings.setIsActive(1L); // making office time active by default before inserting it in db
        return officeTimeMapper.officeTimeToOfficeTimeDto(officeTimingsRepository.save(officeTimings));
    }

    /**
     * method to update office timing status
     *
     * @param status       1/0
     * @param officeTimeId office timing id
     * @return true/false whether status updated or not
     */
    @Transactional
    public Boolean updateOfficeTimeStatus(final Long status, final Long officeTimeId) {
        return 1 == officeTimingsRepository.updateOfficeTimingsStatus(status, officeTimeId);
    }

    /**
     * @param provinceId provinceId
     * @param date       date
     * @return {@link Boolean}
     */
    public Boolean checkIfDateLiesInBetweenFromAndTo(final Long provinceId, final LocalDate date) {
        OfficeTimings usingProvinceIdAndDate = officeTimingsRepository
                .findUsingProvinceIdAndDateAndOfficeTimingType(provinceId, date, OfficeTimingType.OFFICE_HOLIDAY);
        return null != usingProvinceIdAndDate;
    }

    /**
     * @param provinceId provinceId
     * @param fromDate   fromDate
     * @return {@link Long}
     */
    public OfficeTimings getOfficeTimingForFromDate(final Long provinceId, final LocalDate fromDate) {

        return officeTimingsRepository
                .findUsingProvinceIdAndDateAndOfficeTimingType(provinceId, fromDate, OfficeTimingType.OFFICE_TIMING);
    }


    /**
     * @param userId userId
     * @return Collection<OfficeTimeDto>
     */
    public Collection<OfficeTimeDto> getOfficeHolidayByUserId(final Long userId) {
        return officeTimingsRepository
                .findByUserIdAndIsActiveAndOfficeTimingType(userId, 1L, OfficeTimingType.OFFICE_HOLIDAY)
                .stream()
                .map(officeTimeMapper::officeTimeToOfficeTimeDto)
                .collect(Collectors.toList());

    }

    /**
     * @param provinceId provinceId
     * @param fromDate   fromDate
     * @return {@link Long}
     */
    public OfficeTimeDto getOfficeTimingForFromDateAndProvinceId(final Long provinceId, final LocalDate fromDate) {
         OfficeTimings officeTimings = officeTimingsRepository
                .findUsingProvinceIdAndDateAndOfficeTimingType(provinceId, fromDate, OfficeTimingType.OFFICE_TIMING);
         return officeTimeMapper.officeTimeToOfficeTimeDto(officeTimings);
    }

}
