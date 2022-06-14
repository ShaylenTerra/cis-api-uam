package com.dw.ngms.cis.service.mapper.examination.dockets;

import com.dw.ngms.cis.persistence.domains.examination.dockets.MasterDocket;
import com.dw.ngms.cis.service.dto.examination.dockets.MasterDocketDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MasterDocketMapper {
    @Mappings({
            @Mapping(target = "batchNo", source = "batchNo"),
            @Mapping(target = "sgNo", source = "sgNo"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "srNo", source = "srNo"),
            @Mapping(target = "dateReceived", source = "dateReceived"),
            @Mapping(target = "noOfDgms", source = "noOfDgms"),
            @Mapping(target = "planSizeAcceptable", source = "planSizeAcceptable"),
            @Mapping(target = "landSurveyor", source = "landSurveyor"),
            @Mapping(target = "companyName", source = "companyName"),
            @Mapping(target = "submittedBy", source = "submittedBy"),
            @Mapping(target = "returnTo", source = "returnTo"),
            @Mapping(target = "refNumber", source = "refNumber"),
            @Mapping(target = "fileReference", source = "fileReference"),
            @Mapping(target = "receivedBy", source = "receivedBy"),
            @Mapping(target = "examinationFees", source = "examinationFees"),
            @Mapping(target = "checkedBy", source = "checkedBy"),
            @Mapping(target = "recieptNo", source = "recieptNo"),
            @Mapping(target = "docketTypeItemId", source = "docketTypeItemId"),
            @Mapping(target = "docketSubTypeId", source = "docketSubTypeId"),
    })
    MasterDocket masterDocketDtoToMasterDocket (MasterDocketDto masterDocketDto);

    MasterDocketDto masterDocketToMasterDocketDto (MasterDocket examMasterDocket);

}
