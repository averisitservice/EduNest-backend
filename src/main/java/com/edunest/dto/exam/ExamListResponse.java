package com.edunest.dto.exam;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamListResponse {
    private Integer examId;
    private Integer classId;
    private String className;
    private String examName;
    private Integer maxMarks;
    private Integer passMarks;
    private LocalDate examDate;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
