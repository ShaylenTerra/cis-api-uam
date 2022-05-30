package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.domains.workflow.LpiNotes;
import com.dw.ngms.cis.persistence.projection.workflow.NotesTimelineProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 26/03/21, Fri
 **/
@Repository
public interface LpiNotesRepository extends JpaRepository<LpiNotes, Long> {

    Collection<LpiNotes> findAllByLpi(final String lpi);

    @Query(value = "SELECT  USR.FIRSTNAME as firstName,\n" +
            "       USR.SURNAME as surname,\n" +
            "       LPN.NOTEID as noteId,\n" +
            "       LPN.NOTES as notes,\n" +
            "       MLI.CAPTION as noteType,\n" +
            "       LPN.DATED as dated\n" +
            "FROM LPI_NOTES LPN\n" +
            "INNER JOIN USERS USR on LPN.USERID = USR.USERID\n" +
            "INNER JOIN M_LIST_ITEM MLI on LPN.NOTETYPE = MLI.ITEMID where LPN.LPI = :lpi",
            nativeQuery = true)
    Collection<NotesTimelineProjection> findAllNotesUsingLpi(final String lpi);
}
