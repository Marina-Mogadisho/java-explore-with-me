package ru.practicum.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.user.User;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    List<UserDto> toDtoList(List<User> userList);

    UserShortDto toShortDto(User user);

    List<UserShortDto> toShortDtoList(List<User> userList);

    User toEntity(UserShortDto userShortDto);
}