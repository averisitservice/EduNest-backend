package com.edunest.dto.timeTable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimetableRequest {
    private Integer classId;
    private Integer sectionId;
    private Integer workingDayId;
    private Integer timeSlotId;
    private Integer subjectId;
    private Integer teacherId;
}
