package com.airfranceklm.fasttrack.assignment.schedulers;

import com.airfranceklm.fasttrack.assignment.services.HolidayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StatusUpdateScheduler {

    private final HolidayService holidayService;

    /**
     * @implNote A scheduler that runs on a fixed frequency to check Database if there exists
     * any 'REQUESTED' holidays that matches the current time frame. If yes, update the holidays
     * to 'SCHEDULED'
     */
    @Scheduled(cron = "*/1 * * * * *")
    public void updateRequestedHolidaysToScheduled() {
        holidayService.markScheduledHolidays();
    }

    /**
     * @implNote A scheduler that runs on a fixed frequency to check Database if there exists
     * any 'SCHEDULED' holidays that exceeded the holiday time frame. If yes, update the holidays
     * to 'ARCHIVED'
     */
    @Scheduled(cron = "*/1 * * * * *")
    public void updateScheduledHolidaysToArchived() {
        holidayService.markArchivedHolidays();
    }
}
