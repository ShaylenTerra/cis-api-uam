package com.dw.ngms.cis.service;

import com.dw.ngms.cis.connector.FtpService;
import com.dw.ngms.cis.enums.DataViewerRequestStatus;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.dataviewer.DataViewerLog;
import com.dw.ngms.cis.persistence.domains.dataviewer.DataViewerRequest;
import com.dw.ngms.cis.persistence.projection.dataviewer.CustomQueryResultSetProjection;
import com.dw.ngms.cis.persistence.repository.dataviewer.DataViewerLogRepository;
import com.dw.ngms.cis.persistence.repository.dataviewer.DataViewerRepository;
import com.dw.ngms.cis.persistence.repository.dataviewer.DataViewerRequestRepository;
import com.dw.ngms.cis.pubsub.AppEventPublisher;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerConfigurationDto;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerLogDto;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerRequestDto;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerRequestNotification;
import com.dw.ngms.cis.service.event.EventType;
import com.dw.ngms.cis.service.mapper.DataViewerLogMapper;
import com.dw.ngms.cis.service.mapper.DataViewerRequestMapper;
import com.dw.ngms.cis.service.workflow.PdfGeneratorService;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.web.vm.dataviewer.DataViewerRequestVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@Service
@Slf4j
@AllArgsConstructor
public class DataViewerService {

    private final DataViewerRepository dataViewerConfigurationRepository;

    private final DataViewerLogRepository dataViewerLogRepository;

    private final DataViewerRequestRepository dataViewerRequestRepository;

    private final DataViewerLogMapper dataViewerLogMapper;

    private final DataViewerRequestMapper dataViewerRequestMapper;

    private final AppEventPublisher appEventPublisher;

    private final UserService userService;

    private final FtpService ftpService;

    /**
     * @return Collection objectType collection
     */
    public Collection<String> getAllDistinctType() {

        return dataViewerConfigurationRepository.findDistinctObjectType();

    }

    /**
     * @param objectType objectType table/views
     * @return Collection<String>
     */
    public Collection<DataViewerConfigurationDto> getAllObjectNames(final String objectType) {
        return dataViewerConfigurationRepository.findByObjectType(objectType)
                .stream()
                .map(dataViewerConfiguration -> DataViewerConfigurationDto.builder()
                        .objectName(dataViewerConfiguration.getObjectName())
                        .description(dataViewerConfiguration.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * @param tableName tableName
     * @return Collection<String>
     */
    public Collection<String> getAllColumnForTable(final String tableName) {
        return dataViewerConfigurationRepository.getAllColumnForTable(tableName);
    }

    /**
     * @param dataViewerRequestVm {@link DataViewerRequestVm}
     * @return {@link CustomQueryResultSetProjection}
     */
    public CustomQueryResultSetProjection executeCustomQuery(final DataViewerRequestVm dataViewerRequestVm) {

        final DataViewerLog build = DataViewerLog.builder()
                .objectName(dataViewerRequestVm.getObjectName())
                .userid(dataViewerRequestVm.getUserId())
                .query(dataViewerRequestVm.getQuery())
                .dated(LocalDateTime.now())
                .build();

        dataViewerLogRepository.save(build);

        return dataViewerConfigurationRepository
                .getQueryResult(dataViewerRequestVm.getQuery(), dataViewerRequestVm.getObjectName());
    }

    /**
     * @param dataViewerLogDto {@link DataViewerLogDto}
     * @return {@link DataViewerLogDto}
     */
    public DataViewerRequestDto dataViewerDataRequest(final DataViewerLogDto dataViewerLogDto) {

        final DataViewerRequest dataViewerRequest = dataViewerRequestMapper
                .dataViewerLogDtoToDataViewerRequest(dataViewerLogDto);

        dataViewerRequest.setRequestDate(LocalDateTime.now());
        dataViewerRequest.setProcess(DataViewerRequestStatus.IN_PROCESS);

        final DataViewerRequest savedDataViewerRequest = dataViewerRequestRepository.save(dataViewerRequest);

        savedDataViewerRequest.setReferenceId("DVR-0" + savedDataViewerRequest.getId());

        final DataViewerRequest save = dataViewerRequestRepository.save(dataViewerRequest);

        final User userById = userService.getUserById(dataViewerLogDto.getUserid());

        final DataViewerRequestNotification build = DataViewerRequestNotification.builder()
                .templateId(227L)
                .query(save.getQuery())
                .referenceNo(save.getReferenceId())
                .emailId(userById.getEmail())
                .fullName(userById.getFirstName() + " " + userById.getSurname())
                .build();
        appEventPublisher.publishEvent(build, EventType.DATA_VIEWER_REQUEST);

        executeInProgressDataViewerRequestById(save.getId());

        return dataViewerRequestMapper.dataViewerRequestToDataViewerRequestDto(save);
    }

    /**
     * @param userId   userId
     * @param pageable {@link Pageable}
     * @return Page<DataViewerRequestDto>
     */
    public Page<DataViewerRequestDto> getDataViewerRequest(final Long userId, Pageable pageable) {
        return dataViewerRequestRepository.findAllByUserid(userId, pageable)
                .map(dataViewerRequestMapper::dataViewerRequestToDataViewerRequestDto);
    }

    @Async
    public void executeInProgressDataViewerRequestById(final Long requestId) {
        final Collection<DataViewerRequest> allByProcessAndId = dataViewerRequestRepository
                .findAllByProcessAndId(DataViewerRequestStatus.IN_PROCESS, requestId);
        executeInProgressDataRequest(allByProcessAndId);
        final Optional<DataViewerRequest> byId = dataViewerRequestRepository.findById(requestId);
        if (byId.isPresent()) {
            final DataViewerRequest dataViewerRequest = byId.get();
            dataViewerRequest.setProcessDate(LocalDateTime.now());
            dataViewerRequest.setProcess(DataViewerRequestStatus.PROCESSED);
            dataViewerRequest.setTotalRecord(allByProcessAndId.size());
            final DataViewerRequest save = dataViewerRequestRepository.save(dataViewerRequest);


            final User userById = userService.getUserById(save.getUserid());

            final DataViewerRequestNotification dataViewerRequestNotification = DataViewerRequestNotification.builder()
                    .fullName(userById.getFirstName() + " " + userById.getSurname())
                    .emailId(userById.getEmail())
                    .query(save.getQuery())
                    .referenceNo(save.getReferenceId())
                    .ftpLink(save.getReferenceId() + File.separator + save.getId() + ".csv")
                    .templateId(228L)
                    .build();
            appEventPublisher.publishEvent(dataViewerRequestNotification, EventType.DATA_VIEWER_REQUEST_PROCESSED);
        }

    }


    public void executeAllInProgressDataViewerRequest() {
        final Collection<DataViewerRequest> allByProcess = dataViewerRequestRepository
                .findAllByProcess(DataViewerRequestStatus.IN_PROCESS);
        executeInProgressDataRequest(allByProcess);
    }

    private void executeInProgressDataRequest(Collection<DataViewerRequest> allByProcess) {
        allByProcess
                .forEach(dataViewerRequest -> {
                    final String objectName = dataViewerRequest.getObjectName();
                    final String query = dataViewerRequest.getQuery();
                    String path = FileUtils.ROOT_PATH + File.separator + dataViewerRequest.getId() + ".csv";
                    final CustomQueryResultSetProjection queryResult = dataViewerConfigurationRepository
                            .getQueryResult(query, objectName);

                    createCsv(queryResult.getData(), path);

                    try {
                        ftpService.open();
                        ftpService.createDirectory(dataViewerRequest.getReferenceId());
                        ftpService.putFileToDatedPath(new File(path), dataViewerRequest.getReferenceId());
                        ftpService.logout();

                    } catch (IOException e) {
                        log.error(" error while transferring data viewer request csv to ftp ");
                        e.printStackTrace();
                    }

                });
    }


    private void createCsv(Collection<Map<String, Object>> source, String path) {
        final String header = source.stream()
                .flatMap(stringObjectMap -> stringObjectMap.keySet().stream())
                .distinct()
                .collect(Collectors.joining(","));

        try (FileWriter writer = new FileWriter(path, true)) {

            writer.write(header);
            writer.write("\r\n");


            for (Map<String, Object> lmap : source) {
                final String collect = lmap.values().stream()
                        .map(o -> o + "")
                        .collect(Collectors.joining(","));

                writer.write(collect + "");
                writer.write("\r\n");
            }

        } catch (IOException e) {
            log.error("error occurred while create csv file for data viewer request");
            e.printStackTrace();
        }

    }

}
