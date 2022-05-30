package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftDocument;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftDocumentsDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author prateek on 31-12-2021
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDateTime.class}
)
@DecoratedWith(ReservationDraftDocumentsMapperDecorator.class)
public interface ReservationDraftDocumentsMapper {

    @Mappings({
            @Mapping(target = "documentId", source = "documentId"),
            @Mapping(target = "dated", source = "dated", defaultExpression = "java(LocalDateTime.now())"),
            @Mapping(target = "typeId", source = "typeId"),
            @Mapping(target = "name", source = "documentName")
    })
    ReservationDraftDocument reservationDraftDocumentDtoToReservationDraftDocument(ReservationDraftDocumentsDto reservationDraftDocumentsDto);


    @InheritInverseConfiguration
    ReservationDraftDocumentsDto reservationDraftDocumentToReservationDraftDocumentDto(ReservationDraftDocument reservationDraftDocument);

}
