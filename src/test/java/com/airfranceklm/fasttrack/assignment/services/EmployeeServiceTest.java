package com.airfranceklm.fasttrack.assignment.services;

import com.airfranceklm.fasttrack.assignment.config.WebRestControllerAdvice;
import com.airfranceklm.fasttrack.assignment.fixtures.EmployeeDetailsFixtures;
import com.airfranceklm.fasttrack.assignment.models.EmployeeModel;
import com.airfranceklm.fasttrack.assignment.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;


class EmployeeServiceTest {
    private final EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void shouldReturnAllEmployees() {
        Mockito.when(employeeRepository.findAll()).thenReturn(EmployeeDetailsFixtures.getAllEmployeesDAO());
        final List<EmployeeModel> allEmployees = employeeService.getEmployees();
        Assertions.assertThat(allEmployees).hasSize(2);
        Assertions.assertThat(allEmployees.get(0).getName()).isEqualTo("Alwin");
    }

    @Test
    void shouldReturnEmployeeDetailsByEmployeeId() throws WebRestControllerAdvice.CustomException {
        String employeeId = "klm000001";
        Mockito.when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(EmployeeDetailsFixtures.getEmployeebyId());
        final EmployeeModel employeeModel = employeeService.getUserByEmployeeId(employeeId);
        Assertions.assertThat(employeeModel).isNotNull();
        Assertions.assertThat(employeeModel.getName()).isEqualTo("Alwin");
    }

    @Test
    void shouldReturnNullForEmployeeDetailsByEmployeeId()  {
        String employeeId = "klm000001";
        Mockito.when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(EmployeeDetailsFixtures.getEmployeebyId());
        Assertions.assertThatThrownBy(() -> employeeService.getUserByEmployeeId("klm000002"))
                .isInstanceOf(WebRestControllerAdvice.CustomException.class);
    }
}