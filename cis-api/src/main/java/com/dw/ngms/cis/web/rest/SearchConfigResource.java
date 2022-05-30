package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.dto.SearchTypeDto;
import com.dw.ngms.cis.dto.SearchTypeOfficeMappingDto;
import com.dw.ngms.cis.enums.UserTypes;
import com.dw.ngms.cis.persistence.projection.SearchTypeAndFilterProjection;
import com.dw.ngms.cis.service.SearchService;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 10/12/20, Thu
 **/
@BaseResponse
@AllArgsConstructor
@RestController
@RequestMapping(AppConstants.API_BASE_MAPPING + "/search")
public class SearchConfigResource {

    private final SearchService searchService;

    /**
     * @return Collection<String>
     */
    @GetMapping("/userTypes")
    public ResponseEntity<Collection<String>> getAllUserTypes() {
        return ResponseEntity.ok().body(searchService.getUserType());
    }

    /**
     * @param searchTypeParentId searchTypeParentId
     * @return Collection<SearchTypeDto>
     */
    @GetMapping("/config/{searchTypeParentId}")
    public ResponseEntity<Collection<SearchTypeDto>> getSearchTypeConfig(
            @PathVariable @NotNull final Long searchTypeParentId,
            @ApiIgnore
            @SortDefault(sort = {"sortId","name"}, direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok().body(searchService.getSearchConfig(searchTypeParentId, pageable));
    }

    /**
     *
     * @param searchTypeOfficeMappingDto {@link SearchTypeOfficeMappingDto}
     * @return {@link UpdateResponse}
     */
    @PostMapping("/update/config")
    public ResponseEntity<UpdateResponse> updateSearchTypeOfficeMappings(
            @RequestBody @Valid final SearchTypeOfficeMappingDto searchTypeOfficeMappingDto) {
        Boolean aBoolean = searchService.updateSearchTypeOfficeMapping(searchTypeOfficeMappingDto);
        return ResponseEntity.ok()
                .body(UpdateResponse.builder().update(aBoolean).build());
    }

    /**
     * @param searchTypeId searchTypeId
     * @return Collection<SearchTypeOfficeMappingDto>
     */
    @GetMapping("/searchBy")
    public ResponseEntity<Collection<SearchTypeOfficeMappingDto>> getAllSearchTypeOfficeMapping(
            @RequestParam @NotNull final Long searchTypeId, @NotNull UserTypes userTypes) {
        return ResponseEntity.ok().body(searchService.getAllSearchTypeOfficeMapping(searchTypeId, userTypes));
    }

    /**
     * @param provinceId         provinceId
     * @param parentSearchTypeId parentSearchTypeId
     * @return Collection<SearchTypeAndFilterProjection>
     */
    @GetMapping("/getSearchTypeAndFilterForUserType")
    public ResponseEntity<Collection<SearchTypeAndFilterProjection>> getSearchTypeAndFilterForUserType(
            @RequestParam @NotNull final Long provinceId,
            @RequestParam @NotNull final Long parentSearchTypeId,
            @RequestParam final UserTypes userTypes,
            @ApiIgnore @SortDefault(sort = "st.name", direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok()
                .body(searchService.getSearchTypeByProvinceIdAndUserTypeAndParentSearchTypeId(provinceId,
                        userTypes,
                        parentSearchTypeId,
                        pageable));
    }
}
