package com.airfranceklm.fasttrack.assignment.fixtures;

import com.airfranceklm.fasttrack.assignment.dao.EmployeeDAO;
import com.airfranceklm.fasttrack.assignment.models.EmployeeModel;

import java.util.List;

public class EmployeeDetailsFixtures {
    private final static String NAME_1 = "Alwin";
    public static List<EmployeeModel> getAllEmployees() {
        return List.of(
                new EmployeeModel(NAME_1, "klm000001"),
                new EmployeeModel("Tom", "klm000002")
        );
    }

    public static List<EmployeeDAO> getAllEmployeesDAO() {
        return List.of(
                new EmployeeDAO("klm000001", NAME_1 ),
                new EmployeeDAO("Tom", "klm000002")
        );
    }

    public static EmployeeDAO getEmployeebyId() {
        return new EmployeeDAO("klm000001", NAME_1);
    }
}
