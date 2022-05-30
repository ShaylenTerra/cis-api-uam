package com.dw.ngms.cis.mappers;

import com.dw.ngms.cis.dto.SgdataParcelsDto;
import com.dw.ngms.cis.persistence.domain.number.SgdataParcels;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author : prateekgoel
 * @since : 12/05/21, Wed
 **/
@Mapper(componentModel = "spring")
public interface SgDataParcelsMapper {

    @Mappings({
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "documentSubType", source = "documentSubtype"),
            @Mapping(target = "documentSubTypeId", source = "documentSubtypeId"),
            @Mapping(target = "parcel", source = "parcel"),
            @Mapping(target = "documentType", source = "documentType"),
            @Mapping(target = "farmName", source = "farmName"),
            @Mapping(target = "documentTypeId", source = "documentTypeId"),
            @Mapping(target = "localMunicipalityCode", source = "localMunicipalityCode"),
            @Mapping(target = "localMunicipalityName", source = "localMunicipalityName"),
            @Mapping(target = "lpi", source = "lpi"),
            @Mapping(target = "portion", source = "portion"),
            @Mapping(target = "parcelType", source = "parcelType"),
            @Mapping(target = "province", source = "province"),
            @Mapping(target = "provinceId", source = "provinceId"),
            @Mapping(target = "recordId", source = "recordId"),
            @Mapping(target = "recordTypeId", source = "recordTypeId"),
            @Mapping(target = "referenceNumber", source = "referenceNumber"),
            @Mapping(target = "region",  expression = "java(sgdataParcels.getRegion() + \" (\" + sgdataParcels.getRegistrationTownshipName() + \")\" )"),
            @Mapping(target = "schemeName", source = "schemeName"),
            @Mapping(target = "sgNo", source = "sgNo"),
            @Mapping(target = "schemeNumber", source = "schemeNumber"),
            @Mapping(target = "registrationTownshipId", source = "registrationTownshipId"),
            @Mapping(target = "registrationTownshipName", source = "registrationTownshipName")

    })
    SgdataParcelsDto sgdataParcelsToSgdataParcelsDto(SgdataParcels sgdataParcels);

    @InheritInverseConfiguration
    SgdataParcels sgdataParcelsDtoToSgdataParcels(SgdataParcelsDto sgdataParcelsDto);

}
