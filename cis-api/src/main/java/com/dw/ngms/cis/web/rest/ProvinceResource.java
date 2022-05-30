package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.service.dto.province.LocationDto;
import com.dw.ngms.cis.service.dto.province.ProvinceAddressDto;
import com.dw.ngms.cis.service.dto.province.ProvinceDto;
import com.dw.ngms.cis.service.province.ProvinceService;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * Created by nirmal on 2020/11/06.
 */
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/province")
public class ProvinceResource {

	private final ProvinceService provinceService;


	/**
	 * @return Collection<ProvinceContactDto>
	 */
	@GetMapping("/list")
	@ApiPageable
	public ResponseEntity<Collection<ProvinceDto>>
	getAllProvinceAddress(
			@ApiIgnore @SortDefault(sort = "provinceName", direction = Sort.Direction.ASC) final Pageable pageable) {
		Page<ProvinceDto> allProvinceAddress = provinceService
				.getAllProvinceAddress(pageable);

		HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allProvinceAddress);
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.body(allProvinceAddress.getContent());
	}

	/**
	 * @param provinceId provinceId
	 * @return {@link ProvinceAddressDto}
	 */
	@GetMapping("/list/address")
	public ResponseEntity<ProvinceAddressDto>
	getAddressBasedOnProvinceId(@RequestParam @NotNull final Long provinceId) {
		ProvinceAddressDto provinceAddressBasedOnProvinceId = provinceService
				.getProvinceAddressBasedOnProvinceId(provinceId);
		return ResponseEntity.ok().body(provinceAddressBasedOnProvinceId);
	}

	/**
	 * method to save provinceAddress.
	 *
	 * @param provinceAddressDto {@link ProvinceAddressDto}
	 * @return {@link ProvinceAddressDto}
	 */
	@PostMapping("/address")
	public ResponseEntity<ProvinceAddressDto>
	saveProvinceAddress(@RequestBody @Valid final ProvinceAddressDto provinceAddressDto) {
		return ResponseEntity.ok()
				.body(provinceService.saveProvinceAddress(provinceAddressDto));
	}

	/**
	 * @param categories listCode
	 * @param pageable {@link Pageable}
	 * @return <Collection<LocationDto>>
	 */
	@GetMapping("/location-category")
	@ApiPageable
	public ResponseEntity<Collection<LocationDto>>
	ProvinceByCategory(@NotNull @RequestParam final List<String> categories,
											 @ApiIgnore @SortDefault(sort = "caption", direction = Sort.Direction.ASC) final Pageable pageable) {
		Page<LocationDto> locationDtos = provinceService.getLocationByCategories(categories,pageable);
		HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(locationDtos);
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.body(locationDtos.getContent());

	}

	/**
	 * @param locationDto LocationDto
	 */
	@PostMapping(value = "/saveLocationReservationSystem")
	public ResponseEntity<LocationDto> saveLocationReservationSystem(@Valid @RequestBody LocationDto locationDto) {
		return ResponseEntity.ok()
				.body(provinceService.saveLocationReservationSystem(locationDto));
	}

	/**
	 * @param categories listCode
	 * @param pageable {@link Pageable}
	 * @return <Collection<LocationDto>>
	 */
	@GetMapping("/location-reservation-system")
	@ApiPageable
	public ResponseEntity<Collection<LocationDto>>
	getReservationSystemNonProvinceLocations(@NotNull @RequestParam final List<String> categories,@NotNull @RequestParam Long parentBoundaryId,
										  @ApiIgnore @SortDefault(sort = "caption", direction = Sort.Direction.ASC) final Pageable pageable) {
		Page<LocationDto> locationDtos = provinceService.getReservationSystemNonProvince(parentBoundaryId,categories,pageable);
		HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(locationDtos);
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.body(locationDtos.getContent());

	}
}
