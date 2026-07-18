package com.edunest.controller;

import com.edunest.common.ResponseObject;
import com.edunest.configuration.JwtHelper;
import com.edunest.dto.exam.ExamListResponse;
import com.edunest.dto.exam.ExamMarksEntryResponse;
import com.edunest.dto.exam.ExamMarksSaveRequest;
import com.edunest.dto.exam.ExamRequest;
import com.edunest.dto.exam.ReportCardResponse;
import com.edunest.service.ExamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    ExamService examService;

    @Autowired
    JwtHelper jwtHelper;

    private Integer tenant(HttpServletRequest request) {
        String token = jwtHelper.cleanToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        return jwtHelper.extractTenantId(token);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseObject<List<ExamListResponse>>> getExams(
            HttpServletRequest request, @RequestParam(required = false) Integer classId) {

        ResponseObject<List<ExamListResponse>> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(examService.getExams(tenant(request), classId));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseObject<Boolean>> saveExam(
            HttpServletRequest request, @RequestBody ExamRequest examRequest) {

        String token = jwtHelper.cleanToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        Integer tenantId = jwtHelper.extractTenantId(token);
        Integer loginTeacherId = jwtHelper.extractTeacherId(token);

        ResponseObject<Boolean> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(examService.saveExam(tenantId, loginTeacherId, examRequest));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<ResponseObject<Boolean>> deleteExam(
            HttpServletRequest request, @PathVariable Integer examId) {

        ResponseObject<Boolean> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(examService.deleteExam(tenant(request), examId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{examId}/marks/{classId}")
    public ResponseEntity<ResponseObject<ExamMarksEntryResponse>> getMarksEntry(
            HttpServletRequest request,
            @PathVariable Integer examId,
            @PathVariable Integer classId,
            @RequestParam(required = false) Integer sectionId) {

        ResponseObject<ExamMarksEntryResponse> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(examService.getMarksEntry(tenant(request), examId, classId, sectionId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/marks")
    public ResponseEntity<ResponseObject<Boolean>> saveMarks(
            HttpServletRequest request, @RequestBody ExamMarksSaveRequest marksRequest) {

        ResponseObject<Boolean> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(examService.saveMarks(tenant(request), marksRequest));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{examId}/report/{studentId}")
    public ResponseEntity<ResponseObject<ReportCardResponse>> getReportCard(
            HttpServletRequest request,
            @PathVariable Integer examId,
            @PathVariable Integer studentId) {

        ResponseObject<ReportCardResponse> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(examService.getReportCard(tenant(request), examId, studentId));
        return ResponseEntity.ok(response);
    }
}
