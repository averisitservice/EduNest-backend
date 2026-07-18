package com.edunest.dto.exam;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamRequest {
    private Integer examId;
    private Integer classId;
    private String examName;
    private Integer maxMarks;
    private Integer passMarks;
    private LocalDate examDate;
}
