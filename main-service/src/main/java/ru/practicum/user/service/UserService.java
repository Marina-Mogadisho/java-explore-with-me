package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> getUsersByIds(List<Long> ids, Integer from, Integer size);

    List<UserDto> getUsers(Integer from, Integer size);

    void deleteUserById(Long userId);

}
