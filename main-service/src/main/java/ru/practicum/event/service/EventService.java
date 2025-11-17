package ru.practicum.event.service;

import ru.practicum.event.EventSortTypes;
import ru.practicum.event.EventStates;
import ru.practicum.event.dto.*;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventShortDto> getEventsByFilters(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortTypes sortType,
                                           Integer from, Integer size);

    List<EventFullDto> getAdminEventsByFilters(List<Long> users, List<EventStates> states, List<Long> categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto getEventById(Long eventId);

    EventFullDto getPublishedEventById(Long eventId);

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size);

    EventFullDto getUserEventById(Long userId, Long eventId);

    EventFullDto updateUserEvent(Long userId, Long eventId, UpdateUserEventDto updateUserEventDto);

    EventFullDto updateAdminEvent(Long eventId, UpdateAdminEventDto updateAdminEventDto);

    List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResponse updateRequestStates(Long userId, Long eventId,
                                                         EventRequestStatusUpdateRequest updateRequest);
}
