package com.edunest.dto.event;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private Integer eventId;
    private String title;
    private String description;
    private String eventType;
    private String venue;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isAllDay;
    private String audience;
    private Integer classId;
    private String className;
    private String color;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
