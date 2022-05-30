package com.dw.ngms.cis.service.report;

import com.dw.ngms.cis.persistence.domains.OfficeTimings;
import com.dw.ngms.cis.persistence.domains.report.Productivity;
import com.dw.ngms.cis.persistence.domains.report.WorkflowProductivity;
import com.dw.ngms.cis.persistence.repository.report.WorkflowProductivityRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.HolidayCalendarService;
import com.dw.ngms.cis.service.OfficeTimingsService;
import com.dw.ngms.cis.service.user.UserLeaveCalendarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author : prateekgoel
 * @since : 12/02/21, Fri
 **/
@Component
@AllArgsConstructor
@Slf4j
public class ProductivityReportGenerator {

    private final WorkflowRepository workflowRepository;

    private final WorkflowProductivityRepository workflowProductivityRepository;

    private final UserLeaveCalendarService userLeaveCalendarService;

    private final HolidayCalendarService holidayCalendarService;

    private final OfficeTimingsService officeTimingsService;

    public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
        AtomicInteger counter = new AtomicInteger(0);
        return item -> consumer.accept(counter.getAndIncrement(), item);
    }


    @Transactional
    public void generateWorkflowProductivity(final Long workflowId) {
        generate(workflowRepository.getProductivityDataByWorkflowId(workflowId));
    }

    @Transactional
    public void generateWorkflowProductivity() {
        generate(workflowRepository.getProductivityData());
    }

    private void generate(Collection<Productivity> productivities) {
        productivities.forEach(withCounter((i, productivity) -> {
            // delete all data for actionId
            workflowProductivityRepository.deleteAllByActionId(productivity.getActionId());
            LocalDateTime now = LocalDateTime.now();

            if (productivity.getActedOn() == null) {
                productivity.setActedOn(now);
            }

            LocalDateTime postedOn = productivity.getPostedOn();
            LocalDateTime actedOn = productivity.getActedOn();

            Long provinceId = productivity.getProvinceId();
            Long userId = productivity.getUserId();

            while (actedOn.isAfter(postedOn)) {
                WorkflowProductivity workflowProductivity = new WorkflowProductivity();

                LocalDate posted = postedOn.toLocalDate();
                LocalDate acted = actedOn.toLocalDate();

                // check if date is in national holiday list
                if (holidayCalendarService.checkIfHolidayForDate(posted)) {
                    workflowProductivity.setActionId(productivity.getActionId());
                    workflowProductivity.setProductivityDate(postedOn);
                    workflowProductivity.setProductivityMinutes(0L);
                    workflowProductivity.setContext("NATIONAL HOLIDAY");
                    workflowProductivityRepository.save(workflowProductivity);
                } else if (officeTimingsService.checkIfDateLiesInBetweenFromAndTo(provinceId, posted)) {
                    workflowProductivity.setActionId(productivity.getActionId());
                    workflowProductivity.setProductivityDate(postedOn);
                    workflowProductivity.setProductivityMinutes(0L);
                    workflowProductivity.setContext("OFFICE HOLIDAY");
                    workflowProductivityRepository.save(workflowProductivity);
                } else if (userLeaveCalendarService.checkIfDateExistsInUserLeave(userId, posted)) {
                    workflowProductivity.setActionId(productivity.getActionId());
                    workflowProductivity.setProductivityDate(postedOn);
                    workflowProductivity.setProductivityMinutes(0L);
                    workflowProductivity.setContext("USER LEAVE");
                    workflowProductivityRepository.save(workflowProductivity);
                } else if (acted.equals(posted)) {
                    // now-posted
                    LocalTime localTime1 = actedOn.toLocalTime();
                    LocalTime localTime2 = postedOn.toLocalTime();


                    // get office timing for posted date and calculate minutes
                    OfficeTimings officeTimingForFromDate = officeTimingsService
                            .getOfficeTimingForFromDate(provinceId, posted);

                    LocalTime fromTime = officeTimingForFromDate.getFromTime();
                    LocalTime toTime = officeTimingForFromDate.getToTime();

                    if (localTime2.isBefore(fromTime)) {
                        localTime2 = fromTime;
                    }

                    if (localTime1.isAfter(toTime)) {
                        localTime1 = toTime;
                    }

                    long minutes = Duration.between(localTime2, localTime1).toMinutes();

                    workflowProductivity.setActionId(productivity.getActionId());
                    workflowProductivity.setProductivityDate(postedOn);
                    workflowProductivity.setProductivityMinutes(minutes);
                    workflowProductivity.setContext("PRODUCTIVITY");
                    workflowProductivityRepository.save(workflowProductivity);

                } else {

                    OfficeTimings officeTimingForFromDate = officeTimingsService
                            .getOfficeTimingForFromDate(provinceId, posted);

                    LocalTime fromTime = officeTimingForFromDate.getFromTime();
                    LocalTime toTime = officeTimingForFromDate.getToTime();

                    long minutes = Duration.between(fromTime, toTime).toMinutes();

                    workflowProductivity.setActionId(productivity.getActionId());
                    workflowProductivity.setProductivityDate(postedOn);
                    workflowProductivity.setProductivityMinutes(minutes);
                    workflowProductivity.setContext("PRODUCTIVITY");
                    workflowProductivityRepository.save(workflowProductivity);

                }


                postedOn = postedOn.plusDays(1);
            }

            log.debug(" completing iteration {} for actionId {}  ", i, productivity.getActionId());

        }));
    }

}
