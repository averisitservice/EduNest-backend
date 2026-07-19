package com.edunest.dto.announcement;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementRequest {
    private Integer announcementId;
    private String title;
    private String message;
    private String audience;
    private Integer classId;
    private LocalDate publishDate;
}
