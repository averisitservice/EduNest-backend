package com.edunest.dto.homework;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkRequest {
    private Integer homeworkId;
    private Integer classId;
    private Integer sectionId;
    private Integer subjectId;
    private String type;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String attachmentUrl;
}
