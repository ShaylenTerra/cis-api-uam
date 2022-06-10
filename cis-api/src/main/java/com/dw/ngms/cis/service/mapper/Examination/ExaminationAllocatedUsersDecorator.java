package com.dw.ngms.cis.service.mapper.examination;

/**
 * @author Shaylen Budhu on 30-05-2022
 */

import com.dw.ngms.cis.persistence.domains.examination.ExaminationAllocatedUsers;
import com.dw.ngms.cis.service.dto.examination.ExaminationAllocatedUsersDto;

public class ExaminationAllocatedUsersDecorator implements ExaminationAllocatedUsersMapper{
    @Override
    public ExaminationAllocatedUsers ExaminationAllocatedUsersDtoToExaminationAllocatedUsers(ExaminationAllocatedUsersDto examinationAllocatedUsersDto) {
        ExaminationAllocatedUsers examinationAllocatedUsers = new ExaminationAllocatedUsers();

        examinationAllocatedUsers.setScrutinizerId(examinationAllocatedUsersDto.getScrutinizerId());
        examinationAllocatedUsers.setExaminerId(examinationAllocatedUsersDto.getExaminerId());
        examinationAllocatedUsers.setUpdatedByUserId(examinationAllocatedUsersDto.getUpdatedByUserId());
        examinationAllocatedUsers.setModifiedDate(examinationAllocatedUsersDto.getModifiedDate());
        examinationAllocatedUsers.setCreateDate(examinationAllocatedUsersDto.getCreateDate());
        examinationAllocatedUsers.setComments(examinationAllocatedUsersDto.getComments());
        examinationAllocatedUsers.setExamId(examinationAllocatedUsersDto.getExamId());

        return examinationAllocatedUsers;
    }

    @Override
    public ExaminationAllocatedUsersDto ExaminationAllocatedUsersToExaminationAllocatedUsersDto(ExaminationAllocatedUsers examinationAllocatedUsers) {
        ExaminationAllocatedUsersDto examinationAllocatedUsersDto = new ExaminationAllocatedUsersDto();

        examinationAllocatedUsersDto.setScrutinizerId(examinationAllocatedUsers.getScrutinizerId());
        examinationAllocatedUsersDto.setExaminerId(examinationAllocatedUsers.getExaminerId());
        examinationAllocatedUsersDto.setUpdatedByUserId(examinationAllocatedUsers.getUpdatedByUserId());
        examinationAllocatedUsersDto.setModifiedDate(examinationAllocatedUsers.getModifiedDate());
        examinationAllocatedUsersDto.setCreateDate(examinationAllocatedUsers.getCreateDate());
        examinationAllocatedUsersDto.setComments(examinationAllocatedUsers.getComments());
        examinationAllocatedUsersDto.setExamId(examinationAllocatedUsers.getExamId());

        return examinationAllocatedUsersDto;
    }
}
