package com.edunest.dto.timeTable;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkingDayRequest {
    private List<WorkingDayItem> workingDays;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorkingDayItem {
        private String dayName;
        private Integer dayOrder;
        private Boolean isActive;
    }
}
