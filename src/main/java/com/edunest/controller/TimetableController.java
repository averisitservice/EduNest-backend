package com.edunest.controller;

import com.edunest.common.ResponseObject;
import com.edunest.configuration.JwtHelper;
import com.edunest.dto.timeTable.TimeSlotRequest;
import com.edunest.dto.timeTable.TimetableRequest;
import com.edunest.dto.timeTable.TimetableResponse;
import com.edunest.dto.timeTable.WorkingDayRequest;
import com.edunest.entity.TimeSlot;
import com.edunest.entity.WorkingDay;
import com.edunest.service.TimetableService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timetable")
public class TimetableController {

    @Autowired
    TimetableService timetableService;
    
    @Autowired
    JwtHelper jwtHelper;


    @PostMapping("/working-days")
    public ResponseEntity<ResponseObject<Boolean>> saveWorkingDays(HttpServletRequest request, @RequestBody WorkingDayRequest workingDayRequest) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = jwtHelper.cleanToken(authHeader);
        Integer tenantId = jwtHelper.extractTenantId(token);

        ResponseObject<Boolean> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(timetableService.saveWorkingDays(tenantId, workingDayRequest));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/working-days")
    public ResponseEntity<ResponseObject<List<WorkingDay>>> getWorkingDays(HttpServletRequest request) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = jwtHelper.cleanToken(authHeader);
        Integer tenantId = jwtHelper.extractTenantId(token);

        ResponseObject<List<WorkingDay>> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(timetableService.getWorkingDays(tenantId));
        return ResponseEntity.ok(response);
    }


    // POST /school/timetable/time-slots
    @PostMapping("/time-slots")
    public ResponseEntity<ResponseObject<Boolean>> saveTimeSlots(HttpServletRequest request, @RequestBody TimeSlotRequest timeSlotRequest) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = jwtHelper.cleanToken(authHeader);
        Integer tenantId = jwtHelper.extractTenantId(token);

        ResponseObject<Boolean> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(timetableService.saveTimeSlots(tenantId, timeSlotRequest));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/time-slots/{classId}")
    public ResponseEntity<ResponseObject<List<TimeSlot>>> getTimeSlots(HttpServletRequest request, @PathVariable Integer classId) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = jwtHelper.cleanToken(authHeader);
        Integer tenantId = jwtHelper.extractTenantId(token);

        ResponseObject<List<TimeSlot>> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(timetableService.getTimeSlots(tenantId, classId));
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{classId}/{sectionId}")
    public ResponseEntity<ResponseObject<TimetableResponse>> getTimetable(HttpServletRequest request, @PathVariable Integer classId, @PathVariable Integer sectionId) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = jwtHelper.cleanToken(authHeader);
        Integer tenantId = jwtHelper.extractTenantId(token);

        ResponseObject<TimetableResponse> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(timetableService.getTimetable(tenantId, classId, sectionId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cell")
    public ResponseEntity<ResponseObject<Boolean>> saveTimetableCell(HttpServletRequest request, @RequestBody TimetableRequest timetableRequest) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = jwtHelper.cleanToken(authHeader);
        Integer tenantId = jwtHelper.extractTenantId(token);

        ResponseObject<Boolean> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(timetableService.saveTimetableCell(tenantId, timetableRequest));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ResponseObject<TimetableResponse>> getTeacherTimetable(HttpServletRequest request, @PathVariable Integer teacherId) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = jwtHelper.cleanToken(authHeader);
        Integer tenantId = jwtHelper.extractTenantId(token);

        ResponseObject<TimetableResponse> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(timetableService.getTeacherTimetable(tenantId, teacherId));
        return ResponseEntity.ok(response);
    }
}