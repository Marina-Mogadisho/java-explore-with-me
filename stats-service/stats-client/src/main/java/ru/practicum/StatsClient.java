package ru.practicum;

import org.springframework.stereotype.Component;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface StatsClient {

    HitDto addHit(String app, String uri, String ip, LocalDateTime timestamp);

    List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}