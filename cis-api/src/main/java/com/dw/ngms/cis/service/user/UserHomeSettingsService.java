package com.dw.ngms.cis.service.user;

import com.dw.ngms.cis.persistence.domains.user.UserHomeSettings;
import com.dw.ngms.cis.persistence.repository.user.UserHomeSettingsRepository;
import com.dw.ngms.cis.service.dto.user.UserHomeSettingsDto;
import com.dw.ngms.cis.service.mapper.user.UserHomeSettingsMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : prateekgoel
 * @since : 28/11/20, Sat
 **/
@Service
@AllArgsConstructor
@Slf4j
public class UserHomeSettingsService {

    private final UserHomeSettingsRepository userHomeSettingsRepository;

    private final UserHomeSettingsMapper userHomeSettingsMapper;

    /**
     * @param userId user id of user
     * @return UserHomeSettingsDto
     */
    public UserHomeSettingsDto getAllSettings(final Long userId) {
        log.debug("fetching user home page settings for userId {} ", userId);
        return userHomeSettingsRepository.findByUserId(userId)
                .map(userHomeSettingsMapper::userHomeSettingToUserHomeSettingsDto)
                .orElseGet(UserHomeSettingsDto::new);
    }

    /**
     * @param userHomeSettingsDto user home page setting data
     * @return used user home page setting  data
     */
    public UserHomeSettingsDto saveUserHomeSetting(final UserHomeSettingsDto userHomeSettingsDto) {
        log.debug(" saving user home page setting for user {}", userHomeSettingsDto);
        if (null == userHomeSettingsDto)
            return null;

        UserHomeSettings savedUserHomeSettings = userHomeSettingsRepository.save(userHomeSettingsMapper
                .userHomeSettingsDtoToUserHomeSettings(userHomeSettingsDto));

        return userHomeSettingsMapper.userHomeSettingToUserHomeSettingsDto(savedUserHomeSettings);
    }


}
