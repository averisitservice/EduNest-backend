package com.edunest.dto.exam;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamMarksSaveRequest {
    private Integer examId;
    private List<MarkItem> records;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarkItem {
        private Integer studentId;
        private Integer subjectId;
        private BigDecimal marksObtained;
    }
}
