package com.dw.ngms.cis.service.Examination;
import com.dw.ngms.cis.persistence.domains.examination.Examination;
import com.dw.ngms.cis.persistence.domains.province.ProvinceAddress;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.repository.examination.ExaminationRepository;
import com.dw.ngms.cis.security.CurrentLoggedInUser;
import com.dw.ngms.cis.service.dto.Examination.ExaminationDto;
import com.dw.ngms.cis.service.dto.province.ProvinceAddressDto;
import com.dw.ngms.cis.service.mapper.Examination.ExaminationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
@Slf4j
public class ExaminationService {

    private final ExaminationRepository examinationRepository;

    private final CurrentLoggedInUser currentLoggedInUser;

    private final ExaminationMapper examinationMapper;
    public Page<ExaminationDto> getAllExamination(final Pageable pageable){

        SecurityUser user = currentLoggedInUser.getUser();
        Page<Examination> exams = examinationRepository.findAll(pageable);
        return exams.map(examinationMapper::examinaionToExaminationDto);

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

}
