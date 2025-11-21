package ru.practicum.location.service;

import ru.practicum.event.dto.EventLocDto;
import ru.practicum.location.LocationState;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.location.dto.NewLocationDto;
import ru.practicum.location.dto.UpdateLocationDto;
import ru.practicum.location.dto.UserLocationDto;

import java.util.List;

public interface LocationService {
    LocationDto createLocation(NewLocationDto newLocationDto);

    LocationDto updateLocation(Long locId, UpdateLocationDto updateLocationDto);

    List<LocationDto> getAdminLocationsByFilters(LocationState state, Integer from, Integer size);

    List<UserLocationDto> getVisibleUserLocations(Integer from, Integer size);

    UserLocationDto getVisibleLocationById(Long locId);

    LocationDto getLocationById(Long locId);

    List<EventLocDto> getLocationEvents(Long locId, Integer distance, Integer from, Integer size);

    void deleteLocation(Long locId);
}
