package com.airfranceklm.fasttrack.assignment.services;

import com.airfranceklm.fasttrack.assignment.config.WebRestControllerAdvice;
import com.airfranceklm.fasttrack.assignment.dao.EmployeeDAO;
import com.airfranceklm.fasttrack.assignment.models.EmployeeModel;
import com.airfranceklm.fasttrack.assignment.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     *
     * @param employeeModel Employee Details
     *
     * @implNote The method takes employee name from request and create a new entry in EMPLOYEE_DETAILS table
     * and return the new details along with employee ID(auto generated)
     *
     * @return employeeDAO
     */
    public EmployeeDAO saveEmployee(final EmployeeModel employeeModel) {
        final EmployeeDAO employeeDAO = EmployeeDAO
                .builder()
                .name(employeeModel.getName())
                .build();
        return employeeRepository.save(employeeDAO);
    }

    /**
     * @implNote Reads all employee data from DB and casts the result to employee Model
     * @return List of EmployeeModel
     */
    public List<EmployeeModel> getEmployees() {

        return employeeRepository.findAll().stream().map(employeeDAO ->
                new EmployeeModel(employeeDAO.getName(), employeeDAO.getEmployeeId())
        ).collect(Collectors.toList());
    }

    /**
     *
     * @param employeeId Input Employee ID
     * @implNote Search DB with employeeID and cast the corresponding response to employeeModel
     * @return EmployeeModel that matches the input emploeeId
     * @throws WebRestControllerAdvice.CustomException in the case of invalid employee ID
     */
    public EmployeeModel getUserByEmployeeId(final String employeeId) throws WebRestControllerAdvice.CustomException {

        EmployeeDAO employeeDAO = employeeRepository.findByEmployeeId(employeeId);
        if(ObjectUtils.isEmpty(employeeDAO)) {
            throw new WebRestControllerAdvice.CustomException("User not found");
        }
        return new EmployeeModel(employeeDAO.getName(), employeeDAO.getEmployeeId());
    }
}
