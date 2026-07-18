package com.edunest.dto.attendance;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSaveRequest {
    private Integer classId;
    private Integer sectionId;
    private LocalDate attendanceDate;
    private List<AttendanceItem> records;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttendanceItem {
        private Integer studentId;
        private String status;
        private String remarks;
    }
}
