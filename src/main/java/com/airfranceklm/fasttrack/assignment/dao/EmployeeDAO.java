package com.airfranceklm.fasttrack.assignment.dao;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "employee_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmployeeDAO {

    /**
     * @implNote Primary key is generated using a stratergy mentioned in com.airfranceklm.fasttrack.assignment.dao.generator.PrimaryKeyGenerator. 'klm' wil be passed as a prefix
     */
    @Id
    @GeneratedValue(generator = "klm-generator")
    @GenericGenerator(name = "klm-generator",
            parameters = @Parameter(name = "prefix", value = "klm"),
            strategy = "com.airfranceklm.fasttrack.assignment.dao.generator.PrimaryKeyGenerator")
    private String employeeId;
    private String name;

    public EmployeeDAO(String employeeId) {
        this.employeeId = employeeId;
    }
}
