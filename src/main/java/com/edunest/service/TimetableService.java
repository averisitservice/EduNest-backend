package com.edunest.service;

import com.edunest.dto.timeTable.TimeSlotRequest;
import com.edunest.dto.timeTable.TimetableRequest;
import com.edunest.dto.timeTable.TimetableResponse;
import com.edunest.dto.timeTable.WorkingDayRequest;
import com.edunest.entity.TimeSlot;
import com.edunest.entity.WorkingDay;

import java.util.List;

public interface TimetableService {

    boolean saveWorkingDays(Integer tenantId, WorkingDayRequest request);

    List<WorkingDay> getWorkingDays(Integer tenantId);

    boolean saveTimeSlots(Integer tenantId, TimeSlotRequest request);

    List<TimeSlot> getTimeSlots(Integer tenantId, Integer classId);

    TimetableResponse getTimetable(Integer tenantId, Integer classId, Integer sectionId);

    boolean saveTimetableCell(Integer tenantId, TimetableRequest request);

    TimetableResponse getTeacherTimetable(Integer tenantId, Integer teacherId);
}