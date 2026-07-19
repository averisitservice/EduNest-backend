package com.edunest.controller;

import com.edunest.common.ResponseObject;
import com.edunest.configuration.JwtHelper;
import com.edunest.dto.announcement.AnnouncementRequest;
import com.edunest.dto.announcement.AnnouncementResponse;
import com.edunest.service.AnnouncementService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    JwtHelper jwtHelper;

    @GetMapping("/list")
    public ResponseEntity<ResponseObject<List<AnnouncementResponse>>> getAnnouncements(HttpServletRequest request) {
        String token = jwtHelper.cleanToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        Integer tenantId = jwtHelper.extractTenantId(token);

        ResponseObject<List<AnnouncementResponse>> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(announcementService.getAnnouncements(tenantId));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseObject<Boolean>> saveAnnouncement(
            HttpServletRequest request, @RequestBody AnnouncementRequest announcementRequest) {
        String token = jwtHelper.cleanToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        Integer tenantId = jwtHelper.extractTenantId(token);
        Integer loginTeacherId = jwtHelper.extractTeacherId(token);

        ResponseObject<Boolean> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(announcementService.saveAnnouncement(tenantId, loginTeacherId, announcementRequest));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{announcementId}")
    public ResponseEntity<ResponseObject<Boolean>> deleteAnnouncement(
            HttpServletRequest request, @PathVariable Integer announcementId) {
        String token = jwtHelper.cleanToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        Integer tenantId = jwtHelper.extractTenantId(token);

        ResponseObject<Boolean> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(announcementService.deleteAnnouncement(tenantId, announcementId));
        return ResponseEntity.ok(response);
    }
}
