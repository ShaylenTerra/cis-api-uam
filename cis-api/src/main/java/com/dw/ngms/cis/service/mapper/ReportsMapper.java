package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.report.Reports;
import com.dw.ngms.cis.service.dto.report.ReportsDto;
import org.mapstruct.*;

/**
 * @author : prateekgoel
 * @since : 15/06/21, Tue
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportsMapper {

    @Mappings({
            @Mapping(target = "reportId", source = "reportId"),
            @Mapping(target = "reportName", source = "reportName"),
            @Mapping(target = "description", source = "description"),
    })
    ReportsDto reportToReportDto(Reports reports);

    @InheritInverseConfiguration
    Reports reportsDtoToReports(ReportsDto reportsDto);
}
