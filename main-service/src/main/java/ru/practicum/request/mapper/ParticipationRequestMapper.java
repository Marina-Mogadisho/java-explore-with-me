package ru.practicum.request.mapper;

import org.mapstruct.*;
import ru.practicum.request.ParticipationRequest;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParticipationRequestMapper {
    @Mapping(source = "requester", target = "requester.id")
    @Mapping(source = "event", target = "event.id")
    ParticipationRequest toEntity(ParticipationRequestDto participationRequestDto);

    List<ParticipationRequestDto> toParticipationRequestDto(List<ParticipationRequest> participationRequest);

    @InheritInverseConfiguration(name = "toEntity")
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest);
}