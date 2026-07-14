package com.edunest.dto.timeTable;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotRequest {
    private Integer classId;
    private List<TimeSlotItem> timeSlots;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimeSlotItem {
        private Integer timeSlotId;
        private String slotName;
        private LocalTime startTime;
        private LocalTime endTime;
        private Boolean isBreak;
        private Integer orderNo;
    }
}

