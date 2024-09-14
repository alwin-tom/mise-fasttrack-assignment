package com.airfranceklm.fasttrack.assignment.repository;

import com.airfranceklm.fasttrack.assignment.dao.EmployeeDAO;
import com.airfranceklm.fasttrack.assignment.dao.HolidayDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayDAO, UUID> {
    /**
     *
     * @param statusCodes List of status
     * @implNote Get list of Holidays by multiple statusCodes
     * @return List of HolidayDAO
     */
    List<HolidayDAO> findAllByStatusIn(final List<String> statusCodes);

    /**
     *
     * @param statusCode List of status
     * @implNote Get list of Holidays by single statusCode
     * @return List of HolidayDAO
     */
    List<HolidayDAO> findAllByStatus(final String statusCode);

    /**
     *
     * @param employeeDAO Employee Details
     * @implNote Get all holidays applied by and employee
     * @return List of HolidayDAO
     */
    List<HolidayDAO> findAllByEmployeeDetails(final EmployeeDAO employeeDAO);
}
