package com.dw.ngms.cis.service.mapper.sitemap;
import com.dw.ngms.cis.persistence.domains.sitemap.SiteMap;
import com.dw.ngms.cis.service.dto.sitemap.SiteMapDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SiteMapMapper {

    @Mapping(target = "id" , source = "id")
    @Mapping( target = "name", source = "description")
    SiteMapDto siteMapToSiteMapDto(SiteMap siteMap);

    @InheritInverseConfiguration
    SiteMap siteMapDtoToSiteMap(SiteMapDto siteMapDto);

    List<SiteMapDto> collectionSiteMapToCollectionSiteMapDto(List<SiteMap> siteMaps);

    @InheritInverseConfiguration
    List<SiteMap> collectionSiteMapDtoToCollectionSiteMap(List<SiteMapDto> siteMapDtos);
}
