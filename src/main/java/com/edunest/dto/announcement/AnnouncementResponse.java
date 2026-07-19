package com.edunest.dto.announcement;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementResponse {
    private Integer announcementId;
    private String title;
    private String message;
    private String audience;
    private Integer classId;
    private String className;
    private LocalDate publishDate;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
