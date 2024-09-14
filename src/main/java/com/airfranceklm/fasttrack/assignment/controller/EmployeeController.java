package com.airfranceklm.fasttrack.assignment.controller;

import com.airfranceklm.fasttrack.assignment.config.WebRestControllerAdvice;
import com.airfranceklm.fasttrack.assignment.dao.EmployeeDAO;
import com.airfranceklm.fasttrack.assignment.models.EmployeeModel;
import com.airfranceklm.fasttrack.assignment.services.EmployeeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin
@Validated
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(
        title = "Employee APIs",
        description = "All APIs in this section is meant for employee management",
        version = "1.0.0"))
public class EmployeeController {

    private final EmployeeService employeeService;

    @PutMapping
    @Operation(description = "Add a new Employee")
    public ResponseEntity<EmployeeDAO> saveUser(@RequestBody EmployeeModel employeeModel) {
        return new ResponseEntity<>(employeeService.saveEmployee(employeeModel), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(description = "Get all employees")
    public ResponseEntity<List<EmployeeModel>> getAllUsers() {
        return new ResponseEntity<>(employeeService.getEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    @Operation(description = "Get employee by employee ID (path variable)")
    public ResponseEntity<EmployeeModel> getUserByEmployeeId(@PathVariable("employeeId") String employeeId) throws WebRestControllerAdvice.CustomException {
        return new ResponseEntity<>(employeeService.getUserByEmployeeId(employeeId), HttpStatus.OK);
    }
}
