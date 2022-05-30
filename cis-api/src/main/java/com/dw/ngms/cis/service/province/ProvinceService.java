package com.dw.ngms.cis.service.province;

import com.dw.ngms.cis.persistence.domains.prepackage.Location;
import com.dw.ngms.cis.persistence.domains.province.ProvinceAddress;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.repository.prepackage.LocationRepository;
import com.dw.ngms.cis.persistence.repository.province.ProvinceAddressRepository;
import com.dw.ngms.cis.service.dto.province.LocationDto;
import com.dw.ngms.cis.service.dto.province.ProvinceAddressDto;
import com.dw.ngms.cis.service.dto.province.ProvinceDto;
import com.dw.ngms.cis.service.mapper.province.LocationMapper;
import com.dw.ngms.cis.service.mapper.province.ProvinceAddressMapper;
import com.dw.ngms.cis.service.mapper.province.ProvinceMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nirmal on 2020/11/06.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    private final ProvinceAddressRepository provinceAddressRepository;

    private final ProvinceMapper provinceMapper;

    private final ProvinceAddressMapper provinceAddressMapper;

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;
    /**
     * @return {@link ProvinceDto}
     */
    public Page<ProvinceDto> getAllProvinceAddress(final Pageable pageable) {
        return provinceRepository.findAll(pageable)
                .map(provinceMapper::provinceToProvinceDto);
    }

    /**
     * method to fetch provinc address based on provinceId
     *
     * @param provinceId provinceId
     * @return {@link ProvinceAddressDto}
     */
    public ProvinceAddressDto getProvinceAddressBasedOnProvinceId(final Long provinceId) {
        ProvinceAddress byProvinceId = provinceAddressRepository.findByProvinceId(provinceId);
        return provinceAddressMapper.provinceAddressToProvinceAddressDto(byProvinceId);
    }

    /**
     * method to save province address
     *
     * @param provinceAddressDto {@link ProvinceAddressDto}
     * @return {@link ProvinceAddressDto}
     */
    public ProvinceAddressDto saveProvinceAddress(final ProvinceAddressDto provinceAddressDto) {
        ProvinceAddress provinceAddress = provinceAddressMapper
                .provinceAddressDtoToProvinceAddress(provinceAddressDto);

        ProvinceAddress savedProvinceAddress = provinceAddressRepository.save(provinceAddress);

        return provinceAddressMapper.provinceAddressToProvinceAddressDto(savedProvinceAddress);

    }

    /**
     * @param provinceId provinceId
     * @return ProvinceAddress
     */
    public ProvinceAddress getProvinceAddressByProvinceId(final Long provinceId) {
        return provinceAddressRepository.findByProvinceId(provinceId);
    }

    public Page<LocationDto> getLocationByCategories(List<String> categories, Pageable pageable){
       return  locationRepository.findByCategoryIn(categories,pageable)
               .map(locationMapper::LocationToLocationDto);
    }

    public LocationDto saveLocationReservationSystem(LocationDto locationDto) {
        Location location = locationRepository.findLocationByBoundaryId(locationDto.getBoundaryId());
        location.setReservationSystem(locationDto.getReservationSystem());
        locationRepository.save(location);
        return locationDto;
    }

    public Page<LocationDto> getReservationSystemNonProvince(Long parentBoundaryId,List<String> categories, Pageable pageable){
       return locationRepository
                .findByCategoryInAndReservationSystemNotNullAndParentBoundaryId(categories,parentBoundaryId,pageable)
                .map(locationMapper::LocationToLocationDto);
    }


}
