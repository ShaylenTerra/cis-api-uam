package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.domains.system.SystemConfiguration;
import com.dw.ngms.cis.persistence.repository.system.SystemConfigurationRepository;
import com.dw.ngms.cis.service.dto.SystemConfigurationDto;
import com.dw.ngms.cis.service.mapper.SystemConfigurationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 18/05/21, Tue
 **/
@Service
@Slf4j
@AllArgsConstructor
public class SystemService {

    private final SystemConfigurationRepository systemConfigurationRepository;

    private final SystemConfigurationMapper systemConfigurationMapper;

    /**
     * @return Collection<SystemConfigurationDto>
     */
    public Collection<SystemConfigurationDto> getAllSystemConfiguration() {
        return systemConfigurationRepository.findAll()
                .stream().map(systemConfigurationMapper::systemConfigurationToSystemConfigurationDto)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param tag tag
     * @return {@link SystemConfigurationDto}
     */
    public SystemConfigurationDto getSystemConfigurationByTag(final String tag) {
        final SystemConfiguration byTag = systemConfigurationRepository.findByTag(tag);
        return systemConfigurationMapper
                .systemConfigurationToSystemConfigurationDto(byTag);
    }

    /**
     * @param systemConfigurationDto {@link SystemConfigurationDto}
     * @return {@link SystemConfigurationDto}
     */
    public SystemConfigurationDto saveSystemConfiguration(final SystemConfigurationDto systemConfigurationDto) {
        final SystemConfiguration systemConfiguration = systemConfigurationMapper
                .systemConfigurationDtoToSystemConfiguration(systemConfigurationDto);

        final SystemConfiguration save = systemConfigurationRepository.save(systemConfiguration);

        return systemConfigurationMapper.systemConfigurationToSystemConfigurationDto(save);

    }

    /**
     * @param configurationId configurationId
     */
    public void deleteSystemConfiguration(final Long configurationId) {
        systemConfigurationRepository.deleteById(configurationId);
    }

    /**
     * @param systemConfigurationId systemConfigurationId
     * @return {@link SystemConfigurationDto}
     */
    public SystemConfigurationDto getSystemConfiguration(final Long systemConfigurationId) {
        final Optional<SystemConfiguration> byId = systemConfigurationRepository
                .findById(systemConfigurationId);
        return byId.map(systemConfigurationMapper::systemConfigurationToSystemConfigurationDto)
                .orElse(null);
    }


}
