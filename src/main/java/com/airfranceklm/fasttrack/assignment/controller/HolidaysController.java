package com.airfranceklm.fasttrack.assignment.controller;

import java.util.List;
import java.util.UUID;

import com.airfranceklm.fasttrack.assignment.config.WebRestControllerAdvice;
import com.airfranceklm.fasttrack.assignment.dao.HolidayDAO;
import com.airfranceklm.fasttrack.assignment.services.HolidayService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.airfranceklm.fasttrack.assignment.models.HolidayModel;

@Controller
@RequestMapping("/holidays")
@CrossOrigin
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(
        title = "Holiday APIs",
        description = "All APIs in this section is meant for holiday management including new, edit, cancel and list",
        version = "1.0.0"))
public class HolidaysController {

    private final HolidayService holidayService;

    @PutMapping
    @Operation(description = "Add a new Holiday")
    public ResponseEntity<HolidayDAO> saveHolidayDetails(@RequestBody final HolidayModel holidayModel) throws WebRestControllerAdvice.CustomException {
        return new ResponseEntity<>(holidayService.saveHoliday(holidayModel), HttpStatus.CREATED);
    }

    @PostMapping
    @Operation(description = "Edit and existing Holiday")
    public ResponseEntity<HolidayDAO> editHolidayDetails(@RequestBody final HolidayModel holidayModel) throws WebRestControllerAdvice.CustomException {
        return new ResponseEntity<>(holidayService.editHolidays(holidayModel), HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get All holidays of all users")
    public ResponseEntity<List<HolidayModel>> getHolidays() {
        return new ResponseEntity<>(holidayService.getAllHolidays(), HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(description = "Get holidays applied by Employee ID")
    public ResponseEntity<List<HolidayModel>> getHolidaysByEmployeeId(@PathVariable("employeeId") String employeeId) throws WebRestControllerAdvice.CustomException {
        return new ResponseEntity<>(holidayService.getAllHolidaysByEmployeeId(employeeId), HttpStatus.OK);
    }

    @DeleteMapping("/{holidayId}")
    @Operation(description = "Delete a holiday")
    public ResponseEntity<List<Void>> deleteHoliday(@PathVariable("holidayId") UUID holidayId) {
        holidayService.deleteHoliday(holidayId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
