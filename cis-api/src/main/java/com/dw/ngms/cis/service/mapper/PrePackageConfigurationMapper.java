package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.prepackage.PrePackageConfiguration;
import com.dw.ngms.cis.service.dto.prepackage.PrePackageConfigurationDto;
import org.mapstruct.*;

/**
 * @author : prateekgoel
 * @since : 04/01/21, Mon
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrePackageConfigurationMapper {

    @Mappings({
            @Mapping(target = "configurationData", source = "configurationData"),
            @Mapping(target = "prePackageId", source = "prePackageId"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "prePackageDataType", source = "prePackageDataType"),
            @Mapping(target = "cost", source = "cost"),
            @Mapping(target = "folder", source = "folder"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "transactionId", source = "transactionId"),
            @Mapping(target = "active", source = "active"),
            @Mapping(target = "sampleImage", source = "sampleFileName")

    })
    PrePackageConfiguration prePackageConfigurationDtoToPrePackageConfiguration(PrePackageConfigurationDto prePackageConfigurationDto);

    @InheritInverseConfiguration
    PrePackageConfigurationDto prePackageConfigurationToPrePackageConfigurationDto(PrePackageConfiguration prePackageConfiguration);

}
