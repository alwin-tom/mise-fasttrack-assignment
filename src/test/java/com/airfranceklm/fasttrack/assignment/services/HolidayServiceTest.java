package com.airfranceklm.fasttrack.assignment.services;

import com.airfranceklm.fasttrack.assignment.config.WebRestControllerAdvice;
import com.airfranceklm.fasttrack.assignment.dao.HolidayDAO;
import com.airfranceklm.fasttrack.assignment.fixtures.EmployeeDetailsFixtures;
import com.airfranceklm.fasttrack.assignment.fixtures.HolidayDetailsFixtures;
import com.airfranceklm.fasttrack.assignment.models.HolidayModel;
import com.airfranceklm.fasttrack.assignment.models.StatusEnum;
import com.airfranceklm.fasttrack.assignment.repository.EmployeeRepository;
import com.airfranceklm.fasttrack.assignment.repository.HolidayRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

class HolidayServiceTest {
    private final HolidayRepository holidayRepository = Mockito.mock(HolidayRepository.class);
    private final EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
    private HolidayService holidayService;

    @BeforeEach
    public void setUp() {
        holidayService = new HolidayService(holidayRepository, employeeRepository);
    }

    @Test
    void should_save_holiday() throws WebRestControllerAdvice.CustomException {
        Mockito.when(employeeRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(EmployeeDetailsFixtures.getEmployeebyId()));
        Mockito.when(holidayRepository.findAllByStatusIn(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidSavedHolidays());
        Mockito.when(holidayRepository.save(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidHolidayDAO());
        HolidayDAO holidayDAO = holidayService.saveHoliday(new HolidayModel(null, "Label 3", "klm00001", OffsetDateTime.now().plusDays(6), OffsetDateTime.now().plusDays(10), StatusEnum.REQUESTED));
        Assertions.assertThat(holidayDAO.getHolidayId()).isNotNull();
    }

    @Test
    void should_save_error_5_days_prior() {
        Mockito.when(employeeRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(EmployeeDetailsFixtures.getEmployeebyId()));
        Mockito.when(holidayRepository.findAllByStatusIn(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidSavedHolidays());
        Mockito.when(holidayRepository.save(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidHolidayDAO());
        Assertions.assertThatThrownBy(() -> holidayService.saveHoliday(new HolidayModel(
                        null,
                        "Label 3",
                        "klm00001",
                        OffsetDateTime.now().plusDays(2),
                        OffsetDateTime.now().plusDays(10),
                        StatusEnum.REQUESTED)
                ))
                .isInstanceOf(WebRestControllerAdvice.CustomException.class)
                .hasMessage("Holidays should be applied minimum 5 days prior");
    }

    @Test
    void should_save_error_3_day_gap() {
        Mockito.when(employeeRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(EmployeeDetailsFixtures.getEmployeebyId()));
        Mockito.when(holidayRepository.findAllByStatusIn(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidSavedHolidays3DayGap());
        Mockito.when(holidayRepository.save(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidHolidayDAO());
        Assertions.assertThatThrownBy(() -> holidayService.saveHoliday(new HolidayModel(
                        null,
                        "Label 3",
                        "klm00001",
                        OffsetDateTime.now().plusDays(11),
                        OffsetDateTime.now().plusDays(12),
                        StatusEnum.REQUESTED)
                ))
                .isInstanceOf(WebRestControllerAdvice.CustomException.class)
                .hasMessage("There should be a gap of minimum 3 days between requests");
    }

    @Test
    void should_save_error_overlapping() {
        Mockito.when(employeeRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(EmployeeDetailsFixtures.getEmployeebyId()));
        Mockito.when(holidayRepository.findAllByStatusIn(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidSavedHolidaysOverlapping());
        Mockito.when(holidayRepository.save(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidHolidayDAO());
        Assertions.assertThatThrownBy(() -> holidayService.saveHoliday(new HolidayModel(
                        null,
                        "Label 3",
                        "klm00001",
                        OffsetDateTime.now().plusDays(6),
                        OffsetDateTime.now().plusDays(8),
                        StatusEnum.REQUESTED)
                ))
                .isInstanceOf(WebRestControllerAdvice.CustomException.class)
                .hasMessage("There are overlapping days in the holiday requests with others");
    }

    @Test
    void should_update_without_error() throws WebRestControllerAdvice.CustomException {
        Mockito.when(employeeRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(EmployeeDetailsFixtures.getEmployeebyId()));
        Mockito.when(holidayRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(HolidayDetailsFixtures.getValidHolidayDAO()));
        Mockito.when(holidayRepository.findAllByStatusIn(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidSavedHolidays());
        Mockito.when(holidayRepository.save(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidHolidayDAO());
        HolidayDAO holidayDAO = holidayService.editHolidays(new HolidayModel(new UUID(13546,345458), "Label 3", "klm00001", OffsetDateTime.now().plusDays(6), OffsetDateTime.now().plusDays(10), StatusEnum.REQUESTED));
        Assertions.assertThat(holidayDAO).isNotNull();
    }

    @Test
    void should_update_error_invalid_id() {
        Mockito.when(employeeRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(EmployeeDetailsFixtures.getEmployeebyId()));
        Mockito.when(holidayRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());
        Mockito.when(holidayRepository.findAllByStatusIn(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidSavedHolidays());
        Mockito.when(holidayRepository.save(ArgumentMatchers.any()))
                .thenReturn(HolidayDetailsFixtures.getValidHolidayDAO());
        Assertions.assertThatThrownBy(() ->
                holidayService.editHolidays(new HolidayModel(
                        null,
                        "Label 3",
                        "klm00001",
                        OffsetDateTime.now().plusDays(6),
                        OffsetDateTime.now().plusDays(10),
                        StatusEnum.REQUESTED))).isInstanceOf(WebRestControllerAdvice.CustomException.class);
    }

}