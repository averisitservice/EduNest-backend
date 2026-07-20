package com.edunest.controller;

import com.edunest.common.ResponseObject;
import com.edunest.configuration.JwtHelper;
import com.edunest.dto.homework.HomeworkRequest;
import com.edunest.dto.homework.HomeworkResponse;
import com.edunest.service.HomeworkService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    HomeworkService homeworkService;

    @Autowired
    JwtHelper jwtHelper;

    private Integer tenant(HttpServletRequest request) {
        String token = jwtHelper.cleanToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        return jwtHelper.extractTenantId(token);
    }

    @GetMapping("/list/{classId}")
    public ResponseEntity<ResponseObject<List<HomeworkResponse>>> getHomeWorkList(
            HttpServletRequest request,
            @PathVariable Integer classId,
            @RequestParam(required = false) Integer sectionId,
            @RequestParam(required = false) String type) {

        ResponseObject<List<HomeworkResponse>> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(homeworkService.getHomeWorkList(tenant(request), classId, sectionId, type));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseObject<Boolean>> saveHomeWork(
            HttpServletRequest request, @RequestBody HomeworkRequest homeworkRequest) {

        String token = jwtHelper.cleanToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        Integer tenantId = jwtHelper.extractTenantId(token);
        Integer loginTeacherId = jwtHelper.extractTeacherId(token);

        ResponseObject<Boolean> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(homeworkService.saveHomeWork(tenantId, loginTeacherId, homeworkRequest));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{homeworkId}")
    public ResponseEntity<ResponseObject<Boolean>> deleteHomeWork(
            HttpServletRequest request, @PathVariable Integer homeworkId) {

        ResponseObject<Boolean> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(homeworkService.deleteHomeWork(tenant(request), homeworkId));
        return ResponseEntity.ok(response);
    }
}
