package com.edunest.dto.timeTable;

import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimetableResponse {

    private List<String> workingDays;
    private List<TimeSlotRow> rows;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimeSlotRow {
        private Integer timeSlotId;
        private String slotName;
        private LocalTime startTime;
        private LocalTime endTime;
        private Boolean isBreak;
        private Map<String, CellData> cells;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CellData {
        private Integer timetableId;
        private Integer subjectId;
        private String subjectName;
        private Integer teacherId;
        private String teacherName;
    }
}