package ru.practicum.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statslogger.StatsLogger;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;
    private final StatsLogger statsLogger;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid UserDto userDto, HttpServletRequest request) {
        log.info("POST new user {}", userDto);
        statsLogger.logIPAndPath(request);
        return userService.createUser(userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(name = "ids", defaultValue = "") List<Long> ids,
                                  @RequestParam(name = "from", defaultValue = "0") Integer from,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size,
                                  HttpServletRequest request) {
        log.info("GET users ids={}, from={}, size={}", ids, from, size);
        statsLogger.logIPAndPath(request);
        if (ids == null || ids.isEmpty()) {
            return userService.getUsers(from, size);
        }
        return userService.getUsersByIds(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "userId") Long userId,
                           HttpServletRequest request) {
        log.info("DELETE user id={}", userId);
        statsLogger.logIPAndPath(request);
        userService.deleteUserById(userId);
    }
}
