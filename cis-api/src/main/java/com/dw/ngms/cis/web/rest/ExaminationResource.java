package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.domains.examination.Examination;
import com.dw.ngms.cis.service.examination.ExaminationService;
import com.dw.ngms.cis.service.dto.Examination.ExaminationDto;
import com.dw.ngms.cis.service.dto.examination.ExaminationAllocatedUsersDto;
import com.dw.ngms.cis.service.dto.province.ProvinceDto;
import com.dw.ngms.cis.service.examination.ExaminationAllocatedUsersService;
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

@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/examination")
public class ExaminationResource {

    private final ExaminationService examinationService;

    @GetMapping("/list")
    @ApiPageable
    public ResponseEntity  GetAllExaminations(@SortDefault(sort = "name", direction = Sort.Direction.ASC,caseSensitive = false) final Pageable pageable){
        Page<ExaminationDto> allExams = examinationService.getAllExamination(pageable);
        return ResponseEntity.ok().body(allExams.getContent());
    }

    @PostMapping("/create")
    public ResponseEntity<ExaminationDto>  createRole(@Valid @RequestBody ExaminationDto examinationDto)
    {
        return ResponseEntity.ok(examinationService.addExam(examinationDto));
    }

    @PutMapping("/update/{ExamId}")
    public ResponseEntity updateRole(@Valid @RequestBody ExaminationDto examinationDto, @PathVariable long ExamId)
    {
        return ResponseEntity.ok(examinationService.editExam(examinationDto, ExamId));
    }

//    @DeleteMapping("/delete/{ExamId}")
//    public ResponseEntity deleteRole(@Valid @PathVariable int ExamId)
//    {
//        return examinationService.deleteExam(ExamId);
//
//    }

    @GetMapping("/getAllocatedUser")
    public ResponseEntity<ExaminationAllocatedUsersDto> getAllocatedUser(@RequestParam @NotNull final Long examinationId){
        return ResponseEntity.ok().body(examinationService.getExaminationAllocatedUsersById(examinationId));
    }

    @PostMapping("/addNewAllocatedUser")
    public ResponseEntity<ExaminationAllocatedUsersDto> addNewAllocatedUser(@RequestBody final ExaminationAllocatedUsersDto examinationAllocatedUsersDto){
        return ResponseEntity.ok().body(examinationService.addNewExaminationAllocatedUser(examinationAllocatedUsersDto));
    }

    @PostMapping("/updateAllocatedUser")
    public ResponseEntity<ExaminationAllocatedUsersDto> updateAllocatedUser(@RequestBody final ExaminationAllocatedUsersDto examinationAllocatedUsersDto){
        return ResponseEntity.ok().body(examinationService.updateExaminationAllocatedUsers(examinationAllocatedUsersDto));
    }

    @DeleteMapping("/deleteAllocatedUser")
    public void deleteAllocatedUser(@RequestParam @NotNull final Long examinationId){
        examinationService.deleteExaminationAllocatedUsers(examinationId);
    }
}


