package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.dataviewer.DataViewerRequest;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerLogDto;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerRequestDto;
import org.mapstruct.*;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DataViewerRequestMapper {

    @Mappings({
            @Mapping(target = "objectName", source = "objectName"),
            @Mapping(target = "process", source = "process"),
            @Mapping(target = "requestDate", source = "requestDate"),
            @Mapping(target = "query", source = "query"),
            @Mapping(target = "totalRecord", source = "totalRecord"),
            @Mapping(target = "userid", source = "userid"),
            @Mapping(target = "processDate", source = "processDate"),
            @Mapping(target = "id", source = "id")

    })
    DataViewerRequestDto dataViewerRequestToDataViewerRequestDto(DataViewerRequest dataViewerRequest);

    @InheritInverseConfiguration
    DataViewerRequest dataViewerRequestDtoToDataViewerRequest(DataViewerRequestDto dataViewerRequestDto);

    @Mappings({
            @Mapping(target = "userid", source = "userid"),
            @Mapping(target = "query", source = "query"),
            @Mapping(target = "objectName", source = "objectName"),
    })
    DataViewerRequest dataViewerLogDtoToDataViewerRequest(DataViewerLogDto dataViewerLogDto);

}
