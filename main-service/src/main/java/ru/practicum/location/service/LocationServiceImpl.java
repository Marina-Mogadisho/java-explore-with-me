package ru.practicum.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventLocDto;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.location.Location;
import ru.practicum.location.LocationState;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.location.dto.NewLocationDto;
import ru.practicum.location.dto.UpdateLocationDto;
import ru.practicum.location.dto.UserLocationDto;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.repository.LocationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final LocationMapper mapper;

    @Override
    @Transactional
    public LocationDto createLocation(NewLocationDto newLocationDto) {
        Location location = mapper.toEntity(newLocationDto);
        location.setState(LocationState.HIDDEN);
        return mapper.toLocationDto(locationRepository.save(location));
    }

    @Override
    @Transactional
    public LocationDto updateLocation(Long locId, UpdateLocationDto updateLocationDto) {
        Location location = locationRepository.getExistingLocation(locId);
        updateFields(location, updateLocationDto);
        return mapper.toLocationDto(location);
    }

    @Override
    @Transactional
    public void deleteLocation(Long locId) {
        if (locationRepository.existsById(locId)) {
            locationRepository.deleteById(locId);
        } else {
            throw new NotFoundException("Location with id=" + locId + " not found");
        }
    }

    @Override
    public List<LocationDto> getAdminLocationsByFilters(LocationState state, Integer from, Integer size) {
        if (state == null) {
            return mapper.toLocationDto(locationRepository.findPortion(from, size));
        } else {
            return mapper.toLocationDto(locationRepository.findPortionByState(state, from, size));
        }
    }

    @Override
    public List<UserLocationDto> getVisibleUserLocations(Integer from, Integer size) {
        return mapper.toUserLocationDto(locationRepository.findPortionByState(LocationState.VISIBLE, from, size));
    }

    @Override
    public UserLocationDto getVisibleLocationById(Long locId) {
        return mapper.toUserLocationDto(locationRepository.findByIdAndState(locId, LocationState.VISIBLE)
                .orElseThrow(() -> new NotFoundException("No location found, id=" + locId)));
    }

    @Override
    public LocationDto getLocationById(Long locId) {
        return mapper.toLocationDto(locationRepository.findById(locId)
                .orElseThrow(() -> new NotFoundException("No location found, id=" + locId)));
    }

    @Override
    public List<EventLocDto> getLocationEvents(Long locId, Integer distance, Integer from, Integer size) {
        Location location = locationRepository.getExistingLocation(locId);
        return eventRepository.findEventsAroundLocation(
                location.getLatitude(), location.getLongitude(), distance, from, size);
    }

    private void updateFields(Location location, UpdateLocationDto updateLocationDto) {
        String title = updateLocationDto.getTitle();
        if (title != null && !title.isBlank()) {
            location.setTitle(title);
        }
        String description = updateLocationDto.getDescription();
        if (description != null && !description.isBlank()) {
            location.setDescription(description);
        }
        Float lat = updateLocationDto.getLatitude();
        if (lat != null) {
            location.setLatitude(lat);
        }
        Float lon = updateLocationDto.getLongitude();
        if (lon != null) {
            location.setLongitude(lon);
        }
        LocationState state = updateLocationDto.getState();
        if (state != null) {
            location.setState(state);
        }
    }
}
