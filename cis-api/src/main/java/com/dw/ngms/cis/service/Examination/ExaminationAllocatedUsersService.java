package com.dw.ngms.cis.service.examination;

import com.dw.ngms.cis.persistence.domains.examination.ExaminationAllocatedUsers;
import com.dw.ngms.cis.persistence.repository.examination.ExaminationAllocatedUsersRepository;
import com.dw.ngms.cis.service.dto.examination.ExaminationAllocatedUsersDto;
import com.dw.ngms.cis.service.mapper.examination.ExaminationAllocatedUsersMapper;
import com.dw.ngms.cis.utilities.Messages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by shaylen on 2022/05/30.
 */

@Service
@AllArgsConstructor
@Slf4j
public class ExaminationAllocatedUsersService {

    private final Messages messages;



}
