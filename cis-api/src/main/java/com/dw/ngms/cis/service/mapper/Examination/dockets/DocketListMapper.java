package com.dw.ngms.cis.service.mapper.examination.dockets;


import com.dw.ngms.cis.persistence.domains.examination.dockets.DocketList;
import com.dw.ngms.cis.service.dto.examination.dockets.DocketListDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DocketListMapper {

    DocketList DocketListDtoToDocketList(DocketListDto docketListDto);

    @InheritInverseConfiguration
    DocketListDto DocketListToDocketListDto(DocketList docketList);

    //List<DocketListDto> ListOfDocketListToListOfDocketListDto(List<DocketList>docketList,String values);

}
