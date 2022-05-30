package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.workflow.LpiNotes;
import com.dw.ngms.cis.service.dto.workflow.LpiNoteDto;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 26/03/21, Fri
 **/
@Mapper(componentModel = "spring")
public interface LpiNotesMapper {


    LpiNotes lpiNotesDtoToLpiNotes(LpiNoteDto lpiNoteDto);

    LpiNoteDto lipiNoteToLpiNotesDto(LpiNotes lpiNotes);

}
