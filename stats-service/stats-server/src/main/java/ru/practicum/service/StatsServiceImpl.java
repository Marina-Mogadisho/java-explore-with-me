package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.exception.MalformedDataException;
import ru.practicum.mapper.HitMapper;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.Stats;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final HitMapper hitMapper;
    private final StatsMapper statsMapper;
    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public HitDto addHit(HitDto hitDto) {
        return hitMapper.hitToDto(statsRepository.save(hitMapper.dtoToHit(hitDto)));
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start == null || end == null || start.isAfter(end)) {
            throw new MalformedDataException("Starting / ending date are not correct or not specified");
        }
        List<Stats> result;
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                result = statsRepository.getAllUniqueHits(start, end);
            } else {
                result = statsRepository.getAllHits(start, end);
            }
        } else if (unique) {
            result = statsRepository.getAllUniqueHitsByUris(start, end, uris);
        } else {
            result = statsRepository.getAllHitsByUris(start, end, uris);
        }
        return statsMapper.statsListToDto(result);
    }
}
