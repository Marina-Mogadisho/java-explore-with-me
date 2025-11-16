package ru.practicum.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.EventSortTypes;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventService;
import ru.practicum.dto.HitDto;
import ru.practicum.statslogger.StatsLogger;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventPublicController {

    private final EventService eventService;
    private final StatsLogger statsLogger;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsByFilters(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "categories", required = false) List<Long> categoryIds,
            @RequestParam(name = "paid", required = false) Boolean paid,
            @RequestParam(name = "rangeStart", required = false)
            @DateTimeFormat(pattern = HitDto.DATE_FORMAT_PATTERN) LocalDateTime rangeStart,
            @RequestParam(name = "rangeEnd", required = false)
            @DateTimeFormat(pattern = HitDto.DATE_FORMAT_PATTERN) LocalDateTime rangeEnd,
            @RequestParam(name = "onlyAvailable", required = false) Boolean onlyAvailable,
            @RequestParam(name = "sort", required = false) EventSortTypes sortType,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size,
            HttpServletRequest request) {
        log.info("""
                        GET events by filters, text={}, categories={}, paid={},
                        rangeStart={}, rangeEnd={}, onlyAvailable={}, sort={},
                        from={}, size={}""",
                text, categoryIds, paid, rangeStart, rangeEnd, onlyAvailable, sortType, from, size);
        statsLogger.logIPAndPath(request);
        return eventService.getEventsByFilters(text, categoryIds, paid, rangeStart, rangeEnd,
                onlyAvailable, sortType, from, size);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventById(@PathVariable("eventId") Long eventId, HttpServletRequest request) {
        log.info("GET event id={}", eventId);
        statsLogger.logIPAndPath(request);
        return eventService.getPublishedEventById(eventId);
    }
}
