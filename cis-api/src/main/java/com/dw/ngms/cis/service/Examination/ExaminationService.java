package com.dw.ngms.cis.service.examination;
import com.dw.ngms.cis.persistence.domains.examination.Examination;
import com.dw.ngms.cis.persistence.domains.examination.ExaminationAllocatedUsers;
import com.dw.ngms.cis.persistence.domains.province.ProvinceAddress;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.repository.examination.ExaminationAllocatedUsersRepository;
import com.dw.ngms.cis.persistence.repository.examination.ExaminationRepository;
import com.dw.ngms.cis.security.CurrentLoggedInUser;
import com.dw.ngms.cis.service.dto.examination.ExaminationAllocatedUsersDto;
import com.dw.ngms.cis.service.dto.examination.ExaminationDto;
import com.dw.ngms.cis.service.dto.province.ProvinceAddressDto;
import com.dw.ngms.cis.service.mapper.examination.ExaminationMapper;
import com.dw.ngms.cis.service.mapper.examination.ExaminationAllocatedUsersMapper;
import com.dw.ngms.cis.service.mapper.examination.ExaminationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class ExaminationService {

    private final ExaminationRepository examinationRepository;

    private final CurrentLoggedInUser currentLoggedInUser;

    private final ExaminationMapper examinationMapper;

    private final ExaminationAllocatedUsersMapper examinationAllocatedUsersMapper;

    private final ExaminationAllocatedUsersRepository examinationAllocatedUsersRepository;

    public Page<ExaminationDto> getAllExamination(final Pageable pageable){

        SecurityUser user = currentLoggedInUser.getUser();
        Page<Examination> exams = examinationRepository.findAll(pageable);
        return exams.map(examinationMapper::examinaionToExaminationDto);

    }

    public ExaminationDto getExaminationById(Long examinationId){
        Examination examination = examinationRepository.getById(examinationId);
        ExaminationDto examinationDto = examinationMapper.examinaionToExaminationDto(examination);
        return examinationDto;
    }

    public ExaminationDto getExaminationByWorkflowId(Long workflowId){
        Examination examination = examinationRepository.getExaminationByWorkflowId(workflowId);
        ExaminationDto examinationDto = examinationMapper.examinaionToExaminationDto(examination);
        return examinationDto;
    }

    public ExaminationDto saveExam(final ExaminationDto examinationDto) {
        Examination examination = examinationMapper.examinationDtoToExamination(examinationDto);


        Examination savedExam = examinationRepository.save(examination);

        return examinationMapper.examinaionToExaminationDto(savedExam);

    }

    public Examination getExamById(final Long examId) {
        return examinationRepository.findById(examId).get();
    }
    public ExaminationDto addExam(@RequestBody ExaminationDto examinationDto) {


        int checkRole = examinationRepository.CheckExam(examinationDto.getName());
        if (checkRole == 0) {
            LocalDateTime date = LocalDateTime.now();
            examinationDto.setCreatedDate(date);
            Examination examination = examinationMapper.examinationDtoToExamination(examinationDto);
            Examination savedExam= examinationRepository.save(examination);

            return examinationMapper.examinaionToExaminationDto(savedExam);

        }
        else {
            return null;
        }


    }

    public ExaminationDto editExam(@RequestBody ExaminationDto examinationDto , @PathVariable long ExamId) {

        Examination examination = examinationMapper.examinationDtoToExamination(examinationDto);
        Examination exam= examinationRepository.findById(ExamId).get();
        int checkedExam= examinationRepository.CheckExam(examinationDto.getName());


        if(checkedExam==0) {

            exam.setExamId(ExamId);
            exam.setName(examination.getName());
            exam.setCreatedDate(examination.getCreatedDate());
            exam.setAllocatedUserId(examination.getAllocatedUserId());
            exam.setLodgementId(examination.getLodgementId());
            exam.setWorkflowId(examination.getWorkflowId());
            exam.setStatusId(examination.getStatusId());


            examinationRepository.save(exam);
            return examinationMapper.examinaionToExaminationDto(examination);
        }
        return null;
    }

//    public ResponseEntity deleteExam(@PathVariable("ExamId") long ExamId) {
//
//        Examination exam = examinationRepository.findById(ExamId).get();
//        if (exam != null)
//        {
//          return ResponseEntity.ok(examinationRepository.deleteById(ExamId));
//        }
//        return null;
//    }

    public ExaminationAllocatedUsersDto getExaminationAllocatedUsersById(Long examinationId){

        ExaminationAllocatedUsers examinationAllocatedUsers = examinationAllocatedUsersRepository.findAllocatedUserByExamAllocatedId(examinationId);
        ExaminationAllocatedUsersDto examinationAllocatedUsersDto = examinationAllocatedUsersMapper.ExaminationAllocatedUsersToExaminationAllocatedUsersDto(examinationAllocatedUsers);
        return examinationAllocatedUsersDto;
    }

    public ExaminationAllocatedUsersDto addNewExaminationAllocatedUser(ExaminationAllocatedUsersDto examinationAllocatedUsersNew){

        /*ExaminationAllocatedUsers examinationAllocatedUsers = new ExaminationAllocatedUsers();
        examinationAllocatedUsers.setScrutinizerId(examinationAllocatedUsersNew.getScrutinizerId());
        examinationAllocatedUsers.setExaminerId(examinationAllocatedUsersNew.getExaminerId());
        examinationAllocatedUsers.setUpdatedByUserId(examinationAllocatedUsersNew.getUpdatedByUserId());
        examinationAllocatedUsers.setModifiedDate(examinationAllocatedUsersNew.getModifiedDate());
        examinationAllocatedUsers.setCreateDate(examinationAllocatedUsersNew.getCreateDate());
        examinationAllocatedUsers.setComments(examinationAllocatedUsersNew.getComments());
        examinationAllocatedUsers.setExamId(examinationAllocatedUsersNew.getExamId());*/

        LocalDateTime date = LocalDateTime.now();
        examinationAllocatedUsersNew.setCreateDate(date);
        examinationAllocatedUsersNew.setModifiedDate(date);

        ExaminationAllocatedUsers examinationAllocatedUsers = examinationAllocatedUsersMapper.ExaminationAllocatedUsersDtoToExaminationAllocatedUsers(examinationAllocatedUsersNew);
        examinationAllocatedUsersRepository.save(examinationAllocatedUsers);
        ExaminationAllocatedUsersDto examinationAllocatedUsersDto = examinationAllocatedUsersMapper.ExaminationAllocatedUsersToExaminationAllocatedUsersDto(examinationAllocatedUsers);
        return examinationAllocatedUsersDto;
    }

    public ExaminationAllocatedUsersDto updateExaminationAllocatedUsers(ExaminationAllocatedUsersDto examinationAllocatedUsersUpdated){

        ExaminationAllocatedUsers examinationAllocatedUsers = examinationAllocatedUsersRepository.findAllocatedUserByExaminationId(examinationAllocatedUsersUpdated.getExamId());

        examinationAllocatedUsers.setScrutinizerId(examinationAllocatedUsersUpdated.getScrutinizerId());
        examinationAllocatedUsers.setExaminerId(examinationAllocatedUsersUpdated.getExaminerId());
        examinationAllocatedUsers.setUpdatedByUserId(examinationAllocatedUsersUpdated.getUpdatedByUserId());
        //examinationAllocatedUsers.setCreateDate(examinationAllocatedUsersUpdated.getCreateDate());
        examinationAllocatedUsers.setComments(examinationAllocatedUsersUpdated.getComments());
        examinationAllocatedUsers.setExamId(examinationAllocatedUsersUpdated.getExamId());
        LocalDateTime date = LocalDateTime.now();
        examinationAllocatedUsers.setModifiedDate(date);
        examinationAllocatedUsersRepository.save(examinationAllocatedUsers);

        return examinationAllocatedUsersMapper.ExaminationAllocatedUsersToExaminationAllocatedUsersDto(examinationAllocatedUsers);
    }

    public void deleteExaminationAllocatedUsers(Long examinationId){
        ExaminationAllocatedUsers examinationAllocatedUsers = examinationAllocatedUsersRepository.findAllocatedUserByExaminationId(examinationId);
        examinationAllocatedUsersRepository.deleteById(examinationAllocatedUsers.getExamAllocatedId());
    }

}
