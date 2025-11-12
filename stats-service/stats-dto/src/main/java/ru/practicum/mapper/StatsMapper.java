package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.Stats;

import java.util.List;

//(componentModel = "spring") - сгенерированный маппер будет управляемым компонентом Spring,
// и его можно будет использовать как обычный бин в контексте Spring.
@Mapper(componentModel = "spring")
public interface StatsMapper {
    StatsDto statsToDto(Stats stats);

    List<StatsDto> statsListToDto(List<Stats> statsList);
}
