package com.airfranceklm.fasttrack.assignment.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "holiday_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HolidayDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID holidayId;
    private String holidayLabel;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private EmployeeDAO employeeDetails;
    private OffsetDateTime startOfHoliday;
    private OffsetDateTime endOfHoliday;
    private String status;
}
