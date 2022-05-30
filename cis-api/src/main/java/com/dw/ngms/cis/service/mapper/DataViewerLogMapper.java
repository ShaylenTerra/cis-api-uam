package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.dataviewer.DataViewerLog;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerLogDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DataViewerLogMapper {

    DataViewerLog dataViewerLogDtoToDataViewerLog(DataViewerLogDto dataViewerLogDto);

    DataViewerLogDto dataViewerLogToDataViewerLogDto(DataViewerLog dataViewerLog);


}
