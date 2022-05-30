package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.HolidayCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by nirmal on 2020/11/18.
 */
@Repository
public interface HolidayCalendarRepository extends JpaRepository<HolidayCalendar, Long> {

    @Query(value = "SELECT * FROM M_HOLIDAYCALENDAR WHERE HOLIDAY_DATE >= to_date('01-01-' || :YEAR, 'DD.MM.YY') " +
            "and HOLIDAY_DATE < to_date('01-01-' || (:YEAR + 1), 'DD.MM.YY')", nativeQuery = true)
    Collection<HolidayCalendar> findByHolidayYear(@Param("YEAR") Long YEAR);

    @Query(value = "SELECT HC.* FROM M_HOLIDAYCALENDAR HC WHERE HC.STATUS = :status AND TO_DATE(HC.HOLIDAY_DATE) = :holidayDate",
            nativeQuery = true)
    HolidayCalendar findByHolidayDateAndStatus(final LocalDate holidayDate,
                                               final Integer status);

}
