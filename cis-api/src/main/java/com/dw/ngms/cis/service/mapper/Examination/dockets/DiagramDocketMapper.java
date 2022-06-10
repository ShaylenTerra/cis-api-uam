package com.dw.ngms.cis.service.mapper.examination.dockets;
/**
 * @author Shaylen Budhu on 08-06-2022
 */

import com.dw.ngms.cis.persistence.domains.examination.dockets.DiagramDocket;
import com.dw.ngms.cis.service.dto.examination.dockets.DiagramDocketDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DiagramDocketMapper {

    DiagramDocket DiagramDocketDtoToDiagramDocket(DiagramDocketDto diagramDocketDto);

    @InheritInverseConfiguration
    DiagramDocketDto DiagramDocketToDiagramDocketDto(DiagramDocket diagramDocket);

}
