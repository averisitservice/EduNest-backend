package com.edunest.service;

import com.edunest.dto.event.EventRequest;
import com.edunest.dto.event.EventResponse;
import com.edunest.entity.AcademicYear;
import com.edunest.entity.ClassMaster;
import com.edunest.entity.Event;
import com.edunest.entity.Teacher;
import com.edunest.error.CustomException;
import com.edunest.repository.AcademicYearRepository;
import com.edunest.repository.ClassMasterRepository;
import com.edunest.repository.EventRepository;
import com.edunest.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ClassMasterRepository classMasterRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    AcademicYearRepository academicYearRepository;

    private AcademicYear getCurrentYear(Integer tenantId) {
        AcademicYear currentYear = academicYearRepository.findByTenantIdAndIsCurrentTrue(tenantId);
        if (currentYear == null) {
            throw new CustomException("academicYear", "No active academic year found");
        }
        return currentYear;
    }

    private String teacherName(Integer teacherId) {
        if (teacherId == null) return null;
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        return teacher != null ? teacher.getTeacherName() : null;
    }

    @Override
    public List<EventResponse> getEvents(Integer tenantId, LocalDate fromDate, LocalDate toDate) {
        AcademicYear currentYear = getCurrentYear(tenantId);

        List<Event> events;
        if (fromDate != null && toDate != null) {
            events = eventRepository
                    .findByTenantIdAndAcademicYearIdAndIsActiveTrueAndStartDateBetweenOrderByStartDateAscEventIdAsc(
                            tenantId, currentYear.getAcademicYearId(), fromDate, toDate);
        } else {
            events = eventRepository
                    .findByTenantIdAndAcademicYearIdAndIsActiveTrueOrderByStartDateDescEventIdDesc(
                            tenantId, currentYear.getAcademicYearId());
        }

        List<EventResponse> result = new ArrayList<>();
        for (Event e : events) {
            String className = null;
            if (e.getClassId() != null) {
                ClassMaster classMaster = classMasterRepository.findById(e.getClassId()).orElse(null);
                className = classMaster != null ? classMaster.getClassName() : null;
            }

            EventResponse response = new EventResponse();
            response.setEventId(e.getEventId());
            response.setTitle(e.getTitle());
            response.setDescription(e.getDescription());
            response.setEventType(e.getEventType());
            response.setVenue(e.getVenue());
            response.setStartDate(e.getStartDate());
            response.setEndDate(e.getEndDate());
            response.setStartTime(e.getStartTime());
            response.setEndTime(e.getEndTime());
            response.setIsAllDay(e.getIsAllDay());
            response.setAudience(e.getAudience());
            response.setClassId(e.getClassId());
            response.setClassName(className);
            response.setColor(e.getColor());
            response.setCreatedBy(teacherName(e.getCreatedBy()));
            response.setUpdatedBy(teacherName(e.getUpdatedBy()));
            response.setUpdatedDate(e.getUpdatedDate());
            result.add(response);
        }
        return result;
    }

    @Override
    @Transactional
    public boolean saveEvent(Integer tenantId, Integer loginTeacherId, EventRequest request) {
        AcademicYear currentYear = getCurrentYear(tenantId);

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new CustomException("title", "Title is required");
        }
        if (request.getStartDate() == null) {
            throw new CustomException("startDate", "Start date is required");
        }
        if (request.getEndDate() != null && request.getEndDate().isBefore(request.getStartDate())) {
            throw new CustomException("endDate", "End date cannot be before start date");
        }

        boolean allDay = request.getIsAllDay() == null || request.getIsAllDay();
        if (!allDay && request.getStartTime() != null && request.getEndTime() != null
                && request.getEndTime().isBefore(request.getStartTime())) {
            throw new CustomException("endTime", "End time cannot be before start time");
        }

        Event event;
        if (request.getEventId() != null) {
            event = eventRepository.findById(request.getEventId())
                    .orElseThrow(() -> new CustomException("eventId", "Event not found"));

            if (!event.getTenantId().equals(tenantId)) {
                throw new CustomException("eventId", "Event not found");
            }
        } else {
            event = new Event();
            event.setTenantId(tenantId);
            event.setAcademicYearId(currentYear.getAcademicYearId());
            event.setIsActive(true);
            event.setCreatedBy(loginTeacherId);
        }

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventType(request.getEventType() != null ? request.getEventType() : "GENERAL");
        event.setVenue(request.getVenue());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setIsAllDay(allDay);
        event.setStartTime(allDay ? null : request.getStartTime());
        event.setEndTime(allDay ? null : request.getEndTime());
        event.setAudience(request.getAudience() != null ? request.getAudience() : "ALL");
        event.setClassId(request.getClassId());
        event.setColor(request.getColor());
        event.setUpdatedBy(loginTeacherId);
        event.setUpdatedDate(LocalDateTime.now());

        eventRepository.save(event);
        return true;
    }

    @Override
    public boolean deleteEvent(Integer tenantId, Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException("eventId", "Event not found"));

        if (!event.getTenantId().equals(tenantId)) {
            throw new CustomException("eventId", "Event not found");
        }

        event.setIsActive(false);
        eventRepository.save(event);
        return true;
    }
}
