package com.airfranceklm.fasttrack.assignment.services;

import com.airfranceklm.fasttrack.assignment.config.WebRestControllerAdvice.CustomException;
import com.airfranceklm.fasttrack.assignment.dao.EmployeeDAO;
import com.airfranceklm.fasttrack.assignment.dao.HolidayDAO;
import com.airfranceklm.fasttrack.assignment.models.HolidayModel;
import com.airfranceklm.fasttrack.assignment.models.StatusEnum;
import com.airfranceklm.fasttrack.assignment.repository.EmployeeRepository;
import com.airfranceklm.fasttrack.assignment.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private static final String USER_NOT_FOUND = "User not found";

    private final HolidayRepository holidayRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * @param holidayModel
     * @return HolidayDAO
     * @throws CustomException if User not found or invalid dates
     * @implNote Save new holidays based on various conditions
     */
    public HolidayDAO saveHoliday(final HolidayModel holidayModel) throws CustomException {

        EmployeeDAO employeeDAO = employeeRepository.findById(holidayModel.getEmployeeId())
                .orElseThrow(() -> new CustomException((USER_NOT_FOUND)));

        List<HolidayDAO> allHolidayRequests = holidayRepository.findAllByStatusIn(List.of(StatusEnum.REQUESTED.toString(), StatusEnum.SCHEDULED.toString()));
        isValidRequestForSaveAndUpdate(holidayModel, employeeDAO, allHolidayRequests);
        final HolidayDAO holidayDAO = HolidayDAO
                .builder()
                .status(holidayModel.getStatus().toString())
                .endOfHoliday(holidayModel.getEndOfHoliday())
                .startOfHoliday(holidayModel.getStartOfHoliday())
                .employeeDetails(employeeDAO)
                .holidayLabel(holidayModel.getHolidayLabel())
                .build();
        return holidayRepository.save(holidayDAO);

    }

    /**
     * @param holidayModel
     * @return Holiday DAO after update
     * @throws CustomException in case of
     *                         <ul>
     *                             <li>Invalid holiday id</li>
     *                             <li>Invalid Employee ID</li>
     *                             <li>Date rules are not satisfied</li>
     *                         </ul>
     * @implNote Update holidays based on various conditions. Here we omit the current holiday from the conditions
     */
    public HolidayDAO editHolidays(final HolidayModel holidayModel) throws CustomException {

        EmployeeDAO employeeDAO = employeeRepository.findById(holidayModel.getEmployeeId())
                .orElseThrow(() -> new CustomException((USER_NOT_FOUND)));

        holidayRepository.findById(holidayModel.getHolidayId())
                .orElseThrow(() -> new CustomException(("Invalid Holiday Id")));

        List<HolidayDAO> allHolidayRequests = holidayRepository.findAllByStatusIn(List.of(StatusEnum.REQUESTED.toString(), StatusEnum.SCHEDULED.toString()))
                .stream()
                .filter(holidays -> !holidays.getHolidayId().equals(holidayModel.getHolidayId()))
                .collect(Collectors.toList());
        isValidRequestForSaveAndUpdate(holidayModel, employeeDAO, allHolidayRequests);
        final HolidayDAO holidayDAO = HolidayDAO
                .builder()
                .status(holidayModel.getStatus().toString())
                .endOfHoliday(holidayModel.getEndOfHoliday())
                .startOfHoliday(holidayModel.getStartOfHoliday())
                .employeeDetails(employeeDAO)
                .holidayLabel(holidayModel.getHolidayLabel())
                .holidayId(holidayModel.getHolidayId())
                .build();
        return holidayRepository.save(holidayDAO);

    }

    /**
     * @return HolidayModel List
     * @implNote Get list of all Holidays of all employees
     */
    public List<HolidayModel> getAllHolidays() {

        return holidayRepository.findAll().stream().map(holidayDAO ->
                new HolidayModel(
                        holidayDAO.getHolidayId(),
                        holidayDAO.getHolidayLabel(),
                        (holidayDAO.getEmployeeDetails().getEmployeeId()),
                        holidayDAO.getStartOfHoliday(),
                        holidayDAO.getEndOfHoliday(),
                        StatusEnum.fromValue(holidayDAO.getStatus())
                )
        ).collect(Collectors.toList());
    }

    /**
     * @param employeeId
     * @return HolidayModel List
     * @throws CustomException
     * @implNote List of holidays applied by an employee
     */
    public List<HolidayModel> getAllHolidaysByEmployeeId(final String employeeId) throws CustomException {
        EmployeeDAO employeeDAO = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException((USER_NOT_FOUND)));
        return holidayRepository.findAllByEmployeeDetails(employeeDAO).stream().map(holidayDAO ->
                new HolidayModel(
                        holidayDAO.getHolidayId(),
                        holidayDAO.getHolidayLabel(),
                        (holidayDAO.getEmployeeDetails().getEmployeeId()),
                        holidayDAO.getStartOfHoliday(),
                        holidayDAO.getEndOfHoliday(),
                        StatusEnum.fromValue(holidayDAO.getStatus())
                )
        ).collect(Collectors.toList());
    }

    public void deleteHoliday(final UUID holidayId) {
        holidayRepository.deleteById(holidayId);
    }

    /**
     * @implNote Used By scheduler to mark 'REQUESTED' holidays to 'SCHEDULED' if the start date of holiday id less than or equal to current date
     */
    public void markScheduledHolidays() {
        OffsetDateTime currentOffsetdatetime = OffsetDateTime.now();
        List<HolidayDAO> allRequestedHolidays = holidayRepository.findAllByStatus(StatusEnum.REQUESTED.toString());
        allRequestedHolidays.stream()
                .filter(holiday ->
                        holiday.getStartOfHoliday().isBefore(currentOffsetdatetime) || holiday.getStartOfHoliday().isEqual(currentOffsetdatetime)
                )
                .forEach(holiday -> {
                    holiday.setStatus(StatusEnum.SCHEDULED.toString());
                    holidayRepository.save(holiday);
                });
    }

    /**
     * @implNote Used By scheduler to mark 'SCHEDULED' holidays to 'ARCHIVED' if the end date of holiday id after current date
     */
    public void markArchivedHolidays() {
        OffsetDateTime currentOffsetdatetime = OffsetDateTime.now();
        List<HolidayDAO> allRequestedHolidays = holidayRepository.findAllByStatus(StatusEnum.SCHEDULED.toString());
        allRequestedHolidays.stream()
                .filter(holiday -> holiday.getEndOfHoliday().isBefore(currentOffsetdatetime))
                .forEach(holiday -> {
                    holiday.setStatus(StatusEnum.ARCHIVED.toString());
                    holidayRepository.save(holiday);
                });
    }

    /**
     * @param holidayModel
     * @param employeeDAO
     * @param allHolidayRequests
     * @throws CustomException if any criteria fails
     * @implNote Checks if the requested holidays matches all preconditions. Conditions are
     * <ul>
     *     <li>Holidays should be requested atleast 5 days prior</li>
     *     <li>Start and end dates should be valid. End date should be after start date</li>
     *     <li>Requested dates should not overlap with existing holidays</li>
     *     <li>There should be a minimum of 3 days gap between the holidays</li>
     * </ul>
     */
    private void isValidRequestForSaveAndUpdate(final HolidayModel holidayModel,
                                                final EmployeeDAO employeeDAO,
                                                final List<HolidayDAO> allHolidayRequests) throws CustomException {
        if (!isBeforeFiveDays(holidayModel.getStartOfHoliday())) {
            throw new CustomException("Holidays should be applied minimum 5 days prior");
        }
        if (!isValidStartAndEndDate(holidayModel.getStartOfHoliday(), holidayModel.getEndOfHoliday())) {
            throw new CustomException("Invalid start and end time");
        }
        if (isHolidaysOverlapping(holidayModel.getStartOfHoliday(), holidayModel.getEndOfHoliday(), allHolidayRequests)) {
            throw new CustomException("There are overlapping days in the holiday requests with others");
        }
        if (isGapOfThreeDaysExists(holidayModel.getStartOfHoliday(), employeeDAO, allHolidayRequests)) {
            throw new CustomException("There should be a gap of minimum 3 days between requests");
        }
    }

    /**
     * @param startDate
     * @param endDate
     * @return Boolean
     * @implNote Check if start date is before end date and start date is after current date
     */
    private boolean isValidStartAndEndDate(final OffsetDateTime startDate,
                                           final OffsetDateTime endDate) {
        OffsetDateTime currentOffsetdatetime = OffsetDateTime.now();
        return startDate.isBefore(endDate) && startDate.isAfter(currentOffsetdatetime);
    }

    /**
     * @param startDate
     * @return Boolean
     * @implNote Check if the start date is before five days from current day
     */
    private boolean isBeforeFiveDays(final OffsetDateTime startDate) {
        OffsetDateTime currentOffsetdatetime = OffsetDateTime.now();
        return Math.abs(ChronoUnit.DAYS.between(startDate, currentOffsetdatetime)) >= 5;
    }

    /**
     * @param startDate
     * @param employeeDAO
     * @param holidayDAOList
     * @return Boolean
     * @implNote Check if there exists a 3-day gap between applied holidays for this employee
     */
    private boolean isGapOfThreeDaysExists(final OffsetDateTime startDate,
                                           final EmployeeDAO employeeDAO,
                                           final List<HolidayDAO> holidayDAOList) {
        return holidayDAOList.stream()
                .filter(holidayDAO -> holidayDAO.getEmployeeDetails().getEmployeeId().equals(employeeDAO.getEmployeeId()))
                .anyMatch(holidayDAO ->
                        Math.abs(ChronoUnit.DAYS.between(startDate, holidayDAO.getEndOfHoliday())) <= 3
                                || Math.abs(ChronoUnit.DAYS.between(startDate, holidayDAO.getStartOfHoliday())) <= 3
                );
    }

    /**
     * @param startDate
     * @param endDate
     * @param holidayDAOList
     * @return Boolean
     * @implNote Check if the holidays are overlapping with any of the existing requests
     */
    private boolean isHolidaysOverlapping(final OffsetDateTime startDate,
                                          final OffsetDateTime endDate,
                                          final List<HolidayDAO> holidayDAOList) {
        if (holidayDAOList.isEmpty()) {
            return false;
        }
        return holidayDAOList.stream().anyMatch(holidayDAO ->
                startDate.isBefore(holidayDAO.getEndOfHoliday())
                        && endDate.isAfter(holidayDAO.getStartOfHoliday())
        );
    }

}
