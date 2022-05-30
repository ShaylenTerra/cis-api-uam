package com.dw.ngms.cis.persistence.repository.user;

import com.dw.ngms.cis.enums.UserLeaveCalendarStatus;
import com.dw.ngms.cis.persistence.domains.user.UserLeaveCalendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * @author : prateekgoel
 * @since : 04/12/20, Fri
 **/
@Repository
public interface UserLeaveCalenderRepository extends JpaRepository<UserLeaveCalendar, Long> {

    Page<UserLeaveCalendar> findAllByUserId(final Long userId, final Pageable pageable);

    Page<UserLeaveCalendar> findByOrderByLeaveIdDesc(final Pageable pageable);


    @Query(value = "SELECT * FROM USER_LEAVECALENDAR where userid in (select reporting_userid from vw_user_reporting_master where REPORTING_PROVINCEID = :provinceId) order by LEAVEID DESC",
            countQuery = "SELECT * FROM USER_LEAVECALENDAR where userid in (select reporting_userid from vw_user_reporting_master where REPORTING_PROVINCEID = :provinceId)",
            nativeQuery = true)
    Page<UserLeaveCalendar> searchUserLeaveForProvincialAdmin(final Long provinceId, final Pageable pageable);

    @Query(value = "select * from user_leavecalendar where userid in (select userid from vw_user_reporting_master where reporting_userid = :userId) order by LEAVEID DESC",
            countQuery = "select * from user_leavecalendar where userid in (select userid from vw_user_reporting_master where reporting_userid = :userId)",
            nativeQuery = true)
    Page<UserLeaveCalendar> searchUserLeaveForLoggedInUser(final Long userId, final Pageable pageable);

    UserLeaveCalendar findByLeaveId(final Long leaveId);

    @Modifying(flushAutomatically = true)
    @Query(" update UserLeaveCalendar ulc set ulc.status = :userLeaveCalendarStatus,ulc.managerComment = :note where ulc.leaveId =:leaveId")
    int updateLeaveStatus(final Long leaveId, final UserLeaveCalendarStatus userLeaveCalendarStatus,final String note);

    @Query(value = "select ulc.* from USER_LEAVECALENDAR ulc where ulc.STATUS = 2 and ulc.USERID = :userId " +
            "and to_date(to_char(ulc.STARTDATE,'dd-mon-yyyy')) <= :date   and to_date(to_char(ulc.ENDDATE,'dd-mon-yyyy')) >= :date ",
            nativeQuery = true)
    UserLeaveCalendar findIfDateExistsBetweenStartAndEndDate(final Long userId, final LocalDate date);
}
