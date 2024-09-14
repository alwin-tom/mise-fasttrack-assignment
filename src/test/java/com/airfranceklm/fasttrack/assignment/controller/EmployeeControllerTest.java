package com.airfranceklm.fasttrack.assignment.controller;

import com.airfranceklm.fasttrack.assignment.fixtures.EmployeeDetailsFixtures;
import com.airfranceklm.fasttrack.assignment.models.EmployeeModel;
import com.airfranceklm.fasttrack.assignment.services.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

class EmployeeControllerTest {

    private final EmployeeService employeeService = Mockito.mock(EmployeeService.class);
    private EmployeeController employeeController;

    @BeforeEach
    public void setup() {
        employeeController = new EmployeeController(employeeService);
    }

    @Test
    void fetchUserByUsernameEndpoint() {
        Mockito.when(employeeService.getEmployees()).thenReturn(EmployeeDetailsFixtures.getAllEmployees());
        ResponseEntity<List<EmployeeModel>> employeeListResponse = employeeController.getAllUsers();
        Assertions.assertThat(employeeListResponse).isNotNull();
        Assertions.assertThat(employeeListResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(employeeListResponse.getBody()).hasSize(2);

    }

}
