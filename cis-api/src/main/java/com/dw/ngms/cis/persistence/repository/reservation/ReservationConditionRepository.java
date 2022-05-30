package com.dw.ngms.cis.persistence.repository.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author prateek on 03-02-2022
 */
@Repository
public interface ReservationConditionRepository extends JpaRepository<ReservationCondition,Long> {

    List<ReservationCondition> findAllByDraftIdOrderByConditionId(final Long draftId);

}
