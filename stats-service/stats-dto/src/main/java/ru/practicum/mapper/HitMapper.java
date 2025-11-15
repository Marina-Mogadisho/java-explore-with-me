package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.dto.HitDto;
import ru.practicum.model.Hit;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HitMapper {

    HitDto hitToDto(Hit hit);

    Hit dtoToHit(HitDto hitDto);
}
