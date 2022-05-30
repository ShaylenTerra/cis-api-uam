package com.dw.ngms.cis.persistence.projection.workflow;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 08/04/21, Thu
 **/
public interface NotesTimelineProjection {

    @Value("#{target.firstName + ' ' + target.surname}")
    String getUserName();

    Date getDated();

    String getNoteType();

    Long getNoteId();

    String getNotes();

}
