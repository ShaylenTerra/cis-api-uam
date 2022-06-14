package com.dw.ngms.cis.web.rest.dockets;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.domains.examination.dockets.Docket;
import com.dw.ngms.cis.service.dto.examination.ExaminationDto;
import com.dw.ngms.cis.service.dto.examination.dockets.DiagramDocketDto;
import com.dw.ngms.cis.service.dto.examination.dockets.MasterDocketDto;
import com.dw.ngms.cis.service.examination.dockets.DocketService;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/docket")
public class DocketResource {
    /* NB creating the Resource and Service classes as generic as possible to use for multiple dockets of different types */

    private final DocketService docketService;

    /* Master Docket */
    @GetMapping("/masterList")
    @ApiPageable
    public ResponseEntity getAllDockets(@SortDefault(sort = "description", direction = Sort.Direction.ASC,caseSensitive = false) final Pageable pageable){
        Page<MasterDocketDto> allDockets = docketService.getAllMasterDocket(pageable);
        return ResponseEntity.ok().body(allDockets.getContent());
    }

    @GetMapping("/getMasterDocketById")
    public ResponseEntity GetMasterDocketById( @RequestParam @NotNull final Long docketId)
    {
        return ResponseEntity.ok().body(docketService.getMasterDocketById(docketId));
    }

    /* Docket Information */
    @GetMapping("/getDocketByExaminationId")
    public ResponseEntity getDocketByExaminationId(@RequestParam @NotNull final Long examinationId){
        return ResponseEntity.ok().body(docketService.getDocketByExaminationId(examinationId));
    }

    @PostMapping("/addNewDocket")
    public ResponseEntity addDocket(@Valid @RequestBody Docket docket){

        DiagramDocketDto diagramDocketDtoObj = docket.getDiagramDocketDto();
        return ResponseEntity.ok().body(docketService.saveDocket(diagramDocketDtoObj));
    }

    @PostMapping("/updateDocket")
    public ResponseEntity updatedDocket(@Valid @RequestBody Docket docket){
        DiagramDocketDto diagramDocketDtoObj = docket.getDiagramDocketDto();
        return ResponseEntity.ok().body(docketService.saveDocket(diagramDocketDtoObj));
    }

    @GetMapping("/getDocketList")
    public ResponseEntity getDocketList(/*@SortDefault(sort = "docketListId", direction = Sort.Direction.ASC,caseSensitive = false) final Pageable pageable*/){
        return ResponseEntity.ok().body(docketService.getDocketList());
    }

    @GetMapping("/getDocketListByParentId")
    public ResponseEntity getDocketListByParentId(@RequestParam @NotNull final Long parentId){
        return ResponseEntity.ok().body(docketService.getDocketByParentList(parentId));
    }


}
