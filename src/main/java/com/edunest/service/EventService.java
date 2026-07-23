package com.edunest.service;

import com.edunest.dto.event.EventRequest;
import com.edunest.dto.event.EventResponse;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    List<EventResponse> getEvents(Integer tenantId, LocalDate fromDate, LocalDate toDate);

    boolean saveEvent(Integer tenantId, Integer loginTeacherId, EventRequest request);

    boolean deleteEvent(Integer tenantId, Integer eventId);
}
