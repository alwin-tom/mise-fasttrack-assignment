package com.airfranceklm.fasttrack.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class HolidayModel {
    private UUID holidayId;
    private String holidayLabel;
    private String employeeId;
    @DateTimeFormat(pattern="yyyy-mm-dd'T'HH:mm:ss.SSSSSSSSSXXX")
    private OffsetDateTime startOfHoliday;
    @DateTimeFormat(pattern="yyyy-mm-dd'T'HH:mm:ss.SSSSSSSSSXXX")
    private OffsetDateTime endOfHoliday;
    private StatusEnum status;
}
