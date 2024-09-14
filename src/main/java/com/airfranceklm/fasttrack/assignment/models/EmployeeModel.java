package com.airfranceklm.fasttrack.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Validated
public class EmployeeModel {
    @NotEmpty
    @Size(min = 2, message = "should have at least 2 characters")
    private String name;

    private String employeeId;


}
