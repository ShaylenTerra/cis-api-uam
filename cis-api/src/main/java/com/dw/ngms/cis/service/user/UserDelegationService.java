package com.dw.ngms.cis.service.user;

import com.dw.ngms.cis.persistence.domains.user.internal.UserDelegations;
import com.dw.ngms.cis.persistence.repository.user.UserDelegationsRepository;
import com.dw.ngms.cis.service.dto.user.UserDelegationsDto;
import com.dw.ngms.cis.service.mapper.user.UserDelegationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : prateekgoel
 * @since : 19/12/20, Sat
 **/
@Service
@AllArgsConstructor
@Slf4j
public class UserDelegationService {

    private final UserDelegationsRepository userDelegationsRepository;

    private final UserDelegationMapper userDelegationMapper;

    /**
     * @param userId   userId
     * @param pageable {@link Pageable}
     * @return Page<UserDelegationsDto>
     */
    public Page<UserDelegationsDto> getAllUserDelegations(final Long userId, final Pageable pageable) {
        return userDelegationsRepository.findAllByUserId(userId, pageable)
                .map(userDelegationMapper::userDelegationsToUserDelegationDto);
    }

    /**
     * @param userDelegationsDto {@link UserDelegationsDto}
     * @return {@link UserDelegationsDto}
     */
    public UserDelegationsDto saveUserDelegation(final UserDelegationsDto userDelegationsDto) {
        return userDelegationMapper.userDelegationsToUserDelegationDto(
                userDelegationsRepository
                        .save(userDelegationMapper.userDelegationsDtoToUserDelegations(userDelegationsDto))
        );
    }

    /**
     * @param status status
     * @param id     delegationId
     * @return whether requested userDelegations is updated or not
     */
    public Boolean updateUserDelegation(final Long status, final Long id) {
        final UserDelegations byDelegationId = userDelegationsRepository.findByDelegationId(id);
        if(null != byDelegationId) {
            byDelegationId.setStatusItemId(status);
            userDelegationsRepository.save(byDelegationId);
        }
        return true;
    }

}
