package com.airfranceklm.fasttrack.assignment.fixtures;

import com.airfranceklm.fasttrack.assignment.dao.EmployeeDAO;
import com.airfranceklm.fasttrack.assignment.dao.HolidayDAO;
import com.airfranceklm.fasttrack.assignment.models.StatusEnum;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class HolidayDetailsFixtures {
    public static List<HolidayDAO> getValidSavedHolidays() {
        return List.of(
                new HolidayDAO(new UUID(1522455, 654646),
                        "Label 1",
                        new EmployeeDAO("klm000001"),
                        OffsetDateTime.now().minusDays(3),
                        OffsetDateTime.now().minusDays(1),
                        StatusEnum.ARCHIVED.toString()),
                new HolidayDAO(new UUID(15224533, 654646),
                        "Label 2",
                        new EmployeeDAO("klm000002"),
                        OffsetDateTime.now().minusDays(3),
                        OffsetDateTime.now().minusDays(1),
                        StatusEnum.ARCHIVED.toString())
        );
    }

    public static List<HolidayDAO> getValidSavedHolidays3DayGap() {
        return List.of(
                new HolidayDAO(new UUID(1522455, 654646),
                        "Label 1",
                        new EmployeeDAO("klm000002"),
                        OffsetDateTime.now().minusDays(5),
                        OffsetDateTime.now().minusDays(1),
                        StatusEnum.REQUESTED.toString()),
                new HolidayDAO(new UUID(15224533, 654646),
                        "Label 2",
                        new EmployeeDAO("klm000001"),
                        OffsetDateTime.now().plusDays(8),
                        OffsetDateTime.now().plusDays(10),
                        StatusEnum.REQUESTED.toString())
        );
    }

    public static List<HolidayDAO> getValidSavedHolidaysOverlapping() {
        return List.of(
                new HolidayDAO(new UUID(1522455, 654646),
                        "Label 1",
                        new EmployeeDAO("klm000002"),
                        OffsetDateTime.now().minusDays(5),
                        OffsetDateTime.now().minusDays(1),
                        StatusEnum.REQUESTED.toString()),
                new HolidayDAO(new UUID(15224533, 654646),
                        "Label 2",
                        new EmployeeDAO("klm000001"),
                        OffsetDateTime.now().plusDays(1),
                        OffsetDateTime.now().plusDays(20),
                        StatusEnum.REQUESTED.toString())
        );
    }

    public static HolidayDAO getValidHolidayDAO() {
        return new HolidayDAO(new UUID(15224533, 654646),
                "Label 3",
                new EmployeeDAO("klm000002"),
                OffsetDateTime.now().plusDays(6),
                OffsetDateTime.now().plusDays(10),
                StatusEnum.ARCHIVED.toString());
    }

}
