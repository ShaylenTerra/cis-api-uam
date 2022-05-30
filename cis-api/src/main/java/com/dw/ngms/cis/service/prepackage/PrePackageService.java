package com.dw.ngms.cis.service.prepackage;

import com.dw.ngms.cis.connector.FtpService;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.prepackage.PrePackageConfiguration;
import com.dw.ngms.cis.persistence.domains.prepackage.PrePackageExecution;
import com.dw.ngms.cis.persistence.domains.prepackage.PrePackageSubscription;
import com.dw.ngms.cis.persistence.projection.prepackage.*;
import com.dw.ngms.cis.persistence.repository.prepackage.LocationRepository;
import com.dw.ngms.cis.persistence.repository.prepackage.PrePackageConfigurationRepository;
import com.dw.ngms.cis.persistence.repository.prepackage.PrePackageExecutionRepository;
import com.dw.ngms.cis.persistence.repository.prepackage.PrePackageSubscriptionRepository;
import com.dw.ngms.cis.pubsub.AppEventPublisher;
import com.dw.ngms.cis.service.FileStorageService;
import com.dw.ngms.cis.service.UserService;
import com.dw.ngms.cis.service.dto.prepackage.*;
import com.dw.ngms.cis.service.event.EventType;
import com.dw.ngms.cis.service.mapper.PrePackageConfigurationMapper;
import com.dw.ngms.cis.service.mapper.PrePackageExecutionMapper;
import com.dw.ngms.cis.service.mapper.PrepackageSubscriptionMapper;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.web.vm.PrepackageSubscriptionNotificationVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 04/01/21, Mon
 **/
@Service
@Slf4j
@AllArgsConstructor
public class PrePackageService {

    private final PrePackageConfigurationRepository prePackageConfigurationRepository;

    private final PrePackageConfigurationMapper prePackageConfigurationMapper;

    private final FileStorageService fileStorageService;

    private final PrePackageSubscriptionRepository prePackageSubscriptionRepository;

    private final PrepackageSubscriptionMapper prepackageSubscriptionMapper;

    private final UserService userService;

    private final AppEventPublisher publisher;

    private final FtpService ftpService;

    private final PrePackageExecutionRepository prePackageExecutionRepository;

    private final LocationRepository locationRepository;

    private final PrePackageExecutionMapper prePackageExecutionMapper;

    /**
     * @param prePackageConfigurationDto {@link PrePackageConfigurationDto}
     * @return {@link PrePackageConfigurationDto}
     */
    public PrePackageConfigurationDto saveConfiguration(final PrePackageConfigurationDto prePackageConfigurationDto) {

        PrePackageConfiguration prePackageConfiguration = prePackageConfigurationMapper
                .prePackageConfigurationDtoToPrePackageConfiguration(prePackageConfigurationDto);

        PrePackageConfiguration savePackageConfiguration = prePackageConfigurationRepository
                .save(prePackageConfiguration);

        MultipartFile sampleImage = prePackageConfigurationDto.getSampleImageFile();


        String storageFile = savePackageConfiguration.getSampleImage();

        if (null != sampleImage) {
            final String targetLocation = StorageContext.PRE_PACKAGE.getStorageContext() +
                    FileUtils.PATH_SEPARATOR +
                    savePackageConfiguration.getPrePackageId();

            storageFile = fileStorageService.storeFile(sampleImage, targetLocation);
        }

        PrePackageConfiguration byPrePackageId = prePackageConfigurationRepository
                .findByPrePackageId(savePackageConfiguration.getPrePackageId());
        byPrePackageId.setSampleImage(storageFile);

        PrePackageConfiguration prePackageConfiguration1 = prePackageConfigurationRepository.save(byPrePackageId);

        return prePackageConfigurationMapper.prePackageConfigurationToPrePackageConfigurationDto(prePackageConfiguration1);
    }

    public Resource getPrePackageSampleImage(final Long prePackageId) {

        final PrePackageConfiguration byPrePackageId = prePackageConfigurationRepository.findByPrePackageId(prePackageId);
        final String location = StorageContext.PRE_PACKAGE.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                byPrePackageId.getPrePackageId() +
                FileUtils.EXTENSION_SEPARATOR +
                FileUtils.getFileExtension(byPrePackageId.getSampleImage());
        log.debug("loading prepackage sample image from location {}", location);

        return fileStorageService.loadFileAsResource(location);
    }

    /**
     * @return Collection<PrePackageConfigurationDto>
     */
    public Page<PrepackageConfigurationProjection> getPrePackageConfigs(final Pageable pageable) {
        return prePackageConfigurationRepository.getAllPrepackagesWithStatus(pageable);
    }

    /**
     * @return Collection<PrepackageProvince>
     */
    public Collection<PrepackageProvince> getAllProvinces() {
        return locationRepository.getProvince();
    }

    /**
     * @param provinceId provinceId
     * @param pageable   {@link Pageable}
     * @return Collection<PrepackageMinorRegion>
     */
    public Collection<PrepackageMinorRegion> getMinorRegion(final Long provinceId, final Pageable pageable) {
        return locationRepository.getMinorRegion(provinceId, pageable).getContent();
    }

    /**
     * @param provinceId provinceId
     * @param pageable   {@link Pageable}
     * @return Collection<PrepackageMajorRegionOrAdminDistrict>
     */
    public Collection<PrepackageMajorRegionOrAdminDistrict> getMajorRegionOrAdminDistrict(final Long provinceId,
                                                                                          final Pageable pageable) {
        return locationRepository.getMajorRegion(provinceId, pageable).getContent();
    }

    /**
     * @param provinceId provinceId
     * @return Collection<PrepackageMunicipality>
     */
    public Collection<PrepackageMunicipality> getMunicipality(final Long provinceId) {
        return locationRepository.getMunicipality(provinceId);
    }

    /**
     * @param pageable {@link Pageable}
     * @return Page<PrepackageConfigurationProjection>
     */
    public Page<PrepackageConfigurationProjection> getPrepackageConfiguration(final Pageable pageable) {
        return prePackageConfigurationRepository.getAllPrepackages(pageable);
    }

    /**
     * @param prepackageSubscriptionDto {@link PrepackageSubscriptionDto}
     * @return PrepackageSubscriptionDto {@link PrepackageSubscriptionDto}
     */
    public PrepackageSubscriptionDto subscribeToPrepackage(final PrepackageSubscriptionDto prepackageSubscriptionDto) {
        PrePackageSubscription prePackageSubscription = prepackageSubscriptionMapper
                .prepackageSubscriptionDtoToPrepackageSubscription(prepackageSubscriptionDto);

        prePackageSubscription.setSubscriptionDate(new Date());

        // validate if user already have this subscription
        Collection<PrePackageSubscription> allByUserIdAndFrequencyIdAndPrePackageIdAndSubscriptionStatus = prePackageSubscriptionRepository
                .findAllByUserIdAndFrequencyIdAndPrePackageIdAndLocationTypeId(prePackageSubscription.getUserId(),
                        prePackageSubscription.getFrequencyId(), prePackageSubscription.getPrePackageId(),
                        prePackageSubscription.getLocationTypeId());

        if(CollectionUtils.isNotEmpty(allByUserIdAndFrequencyIdAndPrePackageIdAndSubscriptionStatus)) {
            return null;
        }

        PrePackageSubscription packageSubscription = prePackageSubscriptionRepository.save(prePackageSubscription);
        packageSubscription.setReferenceId("PRE-0" + packageSubscription.getSubscriptionId());

        final PrePackageSubscription savedPrepackage = prePackageSubscriptionRepository.save(packageSubscription);

        executeSubscriptionById(savedPrepackage.getSubscriptionId());

        return prepackageSubscriptionMapper
                .prepackageSubscriptionToPrepackageSubscriptionDto(savedPrepackage);

    }

    /**
     * @param userId   userId
     * @param pageable pageable
     * @return {@link PrepackageSubscriptionDto}
     */
    public Page<PrepackageSubscriptionProjection> getAllSubscription(final Long userId, final Pageable pageable) {
        return prePackageSubscriptionRepository.getAllPrepackageSubscription(userId, pageable);
    }

    /**
     * @param userId             userId
     * @param subscriptionId     subscriptionId
     * @param subscriptionStatus subscriptionStatus
     * @return boolean value whether subscription updated or not
     */
    @Transactional
    public Boolean updateUserSubscription(final Long userId, final Long subscriptionId,
                                          final Integer subscriptionStatus) {
        return 1 == prePackageSubscriptionRepository
                .updateSubscriptionStatus(userId, subscriptionId, subscriptionStatus);
    }

    /**
     * @param prepackageSubscriptionNotificationVm {@link PrePackageSubscriptionNotification}
     */
    public void notifyForSubscription(final PrepackageSubscriptionNotificationVm prepackageSubscriptionNotificationVm) {
        User userById = userService.getUserById(prepackageSubscriptionNotificationVm.getUserId());

        if (null != userById) {
            log.debug(" user found for workflow notification having userId {} ", prepackageSubscriptionNotificationVm.getUserId());
            PrePackageSubscriptionNotification prePackageSubscriptionNotification = PrePackageSubscriptionNotification.builder()
                    .fullName(userById.getFirstName() + " " + userById.getSurname())
                    .emailId(userById.getEmail())
                    .templateId(127L)
                    .referenceId(prepackageSubscriptionNotificationVm.getReferenceNo()).build();
            publisher.publishEvent(prePackageSubscriptionNotification, EventType.PREPACKAGE_SUBSCRIPTION_NOTIFICATION);
        } else {
            log.debug(" no user found for workflow notification having userId {}", prepackageSubscriptionNotificationVm.getUserId());
        }
    }

    /**
     * @param subscriptionId subscriptionId
     */
    public void executeSubscriptionById(final Long subscriptionId) {
        log.debug(" executing subscription by subscriptionId [{}]", subscriptionId);
        final Collection<PrePackageSubscription> allBySubscriptionStatusAndSubscriptionId =
                prePackageSubscriptionRepository
                        .findAllBySubscriptionStatusAndSubscriptionId(1, subscriptionId);
        try {

            executeSubscription(allBySubscriptionStatusAndSubscriptionId);

        } catch (IOException e) {
            log.error("error occurred while executing prepackage subscription  for subscriptionId [{}]", subscriptionId);
            e.printStackTrace();
        }
    }


    /**
     * @throws IOException IOException
     */
    public void executeAllSubscriptions() throws IOException {
        log.debug(" executing all subscription ");
        final Collection<PrePackageSubscription> allBySubscriptionStatus =
                prePackageSubscriptionRepository.findAllBySubscriptionStatus(1);
        executeSubscription(allBySubscriptionStatus);
    }

    /**
     * @throws IOException IOException
     */
    public void executeSubscriptionDataChange() throws IOException {
        log.debug(" executing subscription  on data change");
        final Collection<PrePackageSubscription> allBySubscriptionStatusAndProcessData =
                prePackageSubscriptionRepository
                        .findAllBySubscriptionStatusAndProcessData(1, 1);
        executeSubscription(allBySubscriptionStatusAndProcessData);
        allBySubscriptionStatusAndProcessData.forEach(prePackageSubscription -> {
            prePackageSubscription.setProcessData(0);
            prePackageSubscriptionRepository.save(prePackageSubscription);
        });
    }

    /**
     * @param subscriptions subscriptions
     * @throws IOException IOException
     */
    private void executeSubscription(Collection<PrePackageSubscription> subscriptions) throws IOException {
        log.debug(" executing prepackage subscription for [{}] ", subscriptions);

        if (null != subscriptions && subscriptions.size() > 0) {
            log.debug(" total [{}] subscription found ", subscriptions.size());

            subscriptions.forEach(prePackageSubscription -> {

                Long prePackageId = prePackageSubscription.getPrePackageId();
                PrePackageConfiguration byPrePackageId = prePackageConfigurationRepository.findByPrePackageId(prePackageId);
                String folder = byPrePackageId.getFolder();
                String locationId = prePackageSubscription.getLocationId();
                final String referenceId = prePackageSubscription.getReferenceId();
                final Long userId = prePackageSubscription.getUserId();

                if (null != locationId) {

                    String readingPath = folder + File.separator + locationId;

                    log.debug(" reading [{}] path for locationId [{}] ", readingPath, locationId);

                    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));

                    String ftpStoragePath = File.separator + referenceId + File.separator + date;

                    try (Stream<File> file = Files.walk(Paths.get(readingPath))
                            .filter(Files::isRegularFile)
                            .map(Path::toFile)
                    ) {

                        ftpService.open();

                        ftpService.createDirectories(ftpStoragePath);

                        file.forEach(file1 -> {
                            try {

                                ftpService.putFileToDatedPath(file1, ftpStoragePath);

                            } catch (IOException e) {
                                log.error("error occurred while transferring files to ftp");
                                e.printStackTrace();
                            }
                        });

                        ftpService.logout();
                        ftpService.close();

                    } catch (IOException e) {
                        log.error("error occurred while reading file from path [{}]", readingPath);
                        e.printStackTrace();
                    }

                    PrePackageExecution prePackageExecution = new PrePackageExecution();
                    prePackageExecution.setExecutionDate(LocalDateTime.now());
                    prePackageExecution.setSubscriptionId(prePackageSubscription.getSubscriptionId());
                    prePackageExecution.setMessage("SUCCESS");
                    prePackageExecution.setFtpLocation(ftpStoragePath);
                    prePackageExecution.setNotificationStatus("SUCCESS");
                    prePackageExecution.setStatus("SUCCESS");
                    final String frequencyId = prePackageSubscription.getFrequencyId();
                    if (frequencyId.equalsIgnoreCase("MONTHLY")) {
                        prePackageExecution.setNextExecutionDate(LocalDateTime.now().plusMonths(1));
                    } else if (frequencyId.equalsIgnoreCase("YEARLY")) {
                        prePackageExecution.setNextExecutionDate(LocalDateTime.now().plusYears(1));
                    } else if (frequencyId.equalsIgnoreCase("WEEKLY")) {
                        prePackageExecution.setNextExecutionDate(LocalDateTime.now().plusDays(7));
                    } else if (frequencyId.equalsIgnoreCase("DAILY")) {
                        prePackageExecution.setNextExecutionDate(LocalDateTime.now().plusDays(1));
                    }

                    prePackageExecutionRepository.save(prePackageExecution);

                    final User userById = userService.getUserById(userId);
                    final PrePackageSubscriptionExecution prePackageSubscriptionExecution = PrePackageSubscriptionExecution.builder()
                            .ftpLink(ftpStoragePath)
                            .emailId(userById.getEmail())
                            .refNo(referenceId)
                            .fullName(userById.getFirstName() + " " + userById.getSurname())
                            .prepackageName(byPrePackageId.getName())
                            .details(byPrePackageId.getDescription())
                            .templateId(207L)
                            .build();

                    publisher.publishEvent(prePackageSubscriptionExecution, EventType.PREPACKAGE_SUBSCRIPTION_EXECUTION);
                } else {
                    log.debug(" locationId is null so we can't execute subscription");
                }

            });

        } else {
            log.debug(" No subscription found to be executed ");
        }
    }


    public Page<PrePackageExecutionDto> getPrepackageExecutionStatus(final Long subscriptionId,
                                                                     final Pageable pageable) {
        return prePackageExecutionRepository.findAllBySubscriptionId(subscriptionId, pageable)
                .map(prePackageExecutionMapper::prepackageExecutionToPrePackageExecutionDto);
    }
}
