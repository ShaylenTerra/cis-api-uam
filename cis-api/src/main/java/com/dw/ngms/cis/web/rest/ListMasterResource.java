package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.dto.SearchTypeDto;
import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.projection.ngi.NgiSearchDataProjection;
import com.dw.ngms.cis.service.*;
import com.dw.ngms.cis.service.dto.FeeSimulatorDto;
import com.dw.ngms.cis.service.dto.listmanagement.ListItemDataDto;
import com.dw.ngms.cis.service.dto.listmanagement.ListItemDto;
import com.dw.ngms.cis.service.dto.listmanagement.ListMasterDto;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftDto;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftStepsDto;
import com.dw.ngms.cis.service.dto.simulator.LodegementCategoryDto;
import com.dw.ngms.cis.service.dto.simulator.LodegementTypeDto;
import com.dw.ngms.cis.service.dto.user.RolesDto;
import com.dw.ngms.cis.service.reservation.ReservationDraftService;
import com.dw.ngms.cis.service.reservation.ReservationDraftStepsService;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import com.dw.ngms.cis.web.vm.FeeSimulatorVm;
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

/**
 * Created by nirmal on 2020/11/06.
 */
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/list-master")
public class ListMasterResource {

    private final RoleService roleService;

    private final ListItemService listItemService;

    private final ListMasterService listMasterService;

    private final SearchService searchService;

    private final FeeSimulatorService feeSimulatorService;

    private final ReservationDraftService reservationDraftService;

    private final ReservationDraftStepsService reservationDraftStepsService;

    /**
     * @return Collection<ManagementListMasterDto>
     */
    @GetMapping
    @ApiPageable
    public ResponseEntity<Collection<ListMasterDto>>
    getAllMasterList(@ApiIgnore @SortDefault(sort = "caption", direction = Sort.Direction.ASC) final Pageable pageable) {
        Page<ListMasterDto> allManagementListMaster = listMasterService
                .getAllManagementListMaster(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allManagementListMaster);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(allManagementListMaster.getContent());
    }


    /**
     * @param userType {@link UserType}
     * @return Collection<Roles>
     */
    @GetMapping("/list-roles")
    public ResponseEntity<Collection<RolesDto>> listRoles(@RequestParam UserType userType) {
        return ResponseEntity.ok()
                .body(roleService.listRoles(userType));
    }

    /**
     * @param sectionItemId sectionItemId
     * @return Collection<RolesDto>
     */
    @GetMapping("/list-active-role-by-section/{sectionItemId}")
    public ResponseEntity<Collection<RolesDto>> listActiveRoles(@PathVariable final Long sectionItemId) {
        return ResponseEntity.ok()
                .body(roleService.listActiveRolesBySectionId(sectionItemId));
    }

    @GetMapping("/list-all-role-by-section/{sectionItemId}")
    public ResponseEntity<Collection<RolesDto>> listAllRoles(@PathVariable final Long sectionItemId) {
        return ResponseEntity.ok()
                .body(roleService.listAllRolesBySectionId(sectionItemId));
    }

    /**
     * @param rolesDto {@link RolesDto}
     * @return {@link RolesDto}
     */
    @PostMapping("/add-role")
    public ResponseEntity<RolesDto> addRole(@RequestBody @Valid RolesDto rolesDto) {
        return ResponseEntity.ok()
                .body(roleService.addRoles(rolesDto));
    }

    /**
     * @param listCode listCode
     * @param pageable {@link Pageable}
     * @return <Collection<MasterListItemDto>>
     */
    @GetMapping("/list-items")
    @ApiPageable
    public ResponseEntity<Collection<ListItemDto>>
    listItemsByListCode(@NotNull @RequestParam final Long listCode,
                        @ApiIgnore @SortDefault(sort = "caption", direction = Sort.Direction.ASC) final Pageable pageable) {
        Page<ListItemDto> masterListItemDtos = listItemService.listItemsByListCode(listCode, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(masterListItemDtos);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(masterListItemDtos.getContent());

    }

    /**
     * @param listItemDto MasterListItemDto
     * @return MasterListItemDto
     */
    @PostMapping(value = "/add")
    public ResponseEntity<ListItemDto> addListItems(@Valid @RequestBody ListItemDto listItemDto) {
        return ResponseEntity.ok()
                .body(listItemService.createListItem(listItemDto));
    }

    /**
     * @param listItemDto {@link ListItemDto}
     * @return {@link ListItemDto}
     */
    @PostMapping("/updateListItem")
    public ResponseEntity<ListItemDto> updateListItem(@RequestBody @Valid ListItemDto listItemDto) {
        ListItemDto listItemDto1 = listItemService.updateListItem(listItemDto);
        return ResponseEntity.ok()
                .body(listItemDto1);
    }

    /**
     * @param isActive 0/1
     * @param itemId   itemId
     * @return UpdateResponse
     */
    @GetMapping("/update")
    public ResponseEntity<UpdateResponse> updateListItemStatus(@RequestParam @NotNull final Long isActive,
                                                               @RequestParam @NotNull final Long itemId) {
        return ResponseEntity.ok().body(
                UpdateResponse.builder()
                        .update(listItemService.updateItemListStatus(isActive, itemId))
                        .build());
    }

    /**
     * @param setDefault setDefault
     * @param itemId     itemId
     * @param listCode   listCode
     * @return Collection<ListItemDto>
     */
    @GetMapping("/updateDefault")
    public ResponseEntity<Collection<ListItemDto>> updateListItemIsDefault(@RequestParam @NotNull final Long setDefault,
                                                                           @RequestParam @NotNull final Long itemId,
                                                                           @RequestParam @NotNull final Long listCode) {
        return ResponseEntity.ok()
                .body(listItemService.updateItemListDefault(setDefault, listCode, itemId));
    }

    /**
     * @param pageable pageable
     * @return Collection<SearchTypeDto>
     */
    @GetMapping("/ngiDataInformationType")
    public ResponseEntity<Collection<SearchTypeDto>> getSearchTypeAndFilterForUserType(
            @ApiIgnore @SortDefault(sort = "name", direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok()
                .body(searchService.getSearchTypeByParentSearchTypeId(
                        16L,
                        pageable));
    }

    /**
     * @param categoryId categoryId
     * @return Collection<NgiSearchDataProjection>
     */
    @GetMapping("/ngiDataInformationCategory")
    public ResponseEntity<Collection<NgiSearchDataProjection>> getNgiDataInformationCategory(@RequestParam Long categoryId) {
        return ResponseEntity.ok()
                .body(listMasterService.getNgiSearchData(categoryId));
    }

    /**
     * @param feeSimulatorVm {@link FeeSimulatorVm}
     * @return {@link FeeSimulatorDto}
     */
    @PostMapping("/feeSimulator")
    public ResponseEntity<FeeSimulatorDto> getFeeSimulatorResult(@RequestBody FeeSimulatorVm feeSimulatorVm) {
        return ResponseEntity.ok()
                .body(feeSimulatorService.calculateFee(feeSimulatorVm));
    }

    /**
     * @return Collection<LodegementTypeDto>
     */
    @GetMapping("/feeSimulator/lodgementType")
    public ResponseEntity<Collection<LodegementTypeDto>> getLodegementType() {
        Collection<LodegementTypeDto> lodegementType = feeSimulatorService.getLodegementType();
        return ResponseEntity.ok()
                .body(lodegementType);
    }

    /**
     * @param categoryId categoryId
     * @return Collection<LodegementCategoryDto>
     */
    @GetMapping("/feeSimulator/lodgementCategory")
    public ResponseEntity<Collection<LodegementCategoryDto>> getLodegementCategory(@RequestParam Long categoryId) {
        Collection<LodegementCategoryDto> lodegementCategories = feeSimulatorService.getLodegementCategories(categoryId);
        return ResponseEntity.ok()
                .body(lodegementCategories);
    }


    /**
     * @param listCode       listCode
     * @param pageable       {@link Pageable}
     * @param dataTypeItemId DataTypeItemId
     * @return <Collection<MasterListItemDto>>
     */
    @GetMapping("/list-items-data")
    @ApiPageable
    public ResponseEntity<Collection<ListItemDataDto>>
    listItemsDataByListCodeAndDataTypeItemId(@NotNull @RequestParam final Long listCode, @NotNull @RequestParam final Long dataTypeItemId,
                                             @ApiIgnore @SortDefault(sort = "caption", direction = Sort.Direction.ASC) final Pageable pageable) {
        Page<ListItemDataDto> listItemDataDtos = listItemService.listItemsDataByListCodeAndDataTypeItemId(listCode, dataTypeItemId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(listItemDataDtos);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(listItemDataDtos.getContent());

    }

    /**
     * @param listItemDataDtos ListItemDataDto
     */
    @PostMapping(value = "/saveListDataColl")
    public ResponseEntity<Collection<ListItemDataDto>> saveListItemDataColl(@Valid @RequestBody Collection<ListItemDataDto> listItemDataDtos) {
        return ResponseEntity.ok()
                .body(listItemService.saveListItemDataColl(listItemDataDtos));
    }

    /**
     * @param listItemDataDto ListItemDataDto
     */
    @PostMapping(value = "/saveListData")
    public ResponseEntity<ListItemDataDto> saveListItemData(@Valid @RequestBody ListItemDataDto listItemDataDto) {
        return ResponseEntity.ok()
                .body(listItemService.saveListItemData(listItemDataDto));
    }

    @PostMapping("/saveDraft")
    public ResponseEntity<ReservationDraftDto>
    saveReservationDraft(@RequestBody final ReservationDraftDto reservationDraftDto) {
        return ResponseEntity.ok().body(reservationDraftService.saveReservationDraft(reservationDraftDto));
    }

    @PostMapping("/saveDraftSteps")
    public ResponseEntity<ReservationDraftStepsDto>
    saveReservationDraftSteps(@RequestBody final ReservationDraftStepsDto reservationDraftStepsDto) {
        return ResponseEntity.ok().body(reservationDraftStepsService.saveReservationDraftSteps(reservationDraftStepsDto));
    }
}
