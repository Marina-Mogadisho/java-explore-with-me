package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (!userRepository.existsByEmailIgnoreCase(userDto.getEmail())) {
            return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
        } else {
            throw new ValidationException("Email " + userDto.getEmail() + " alredy in use");
        }
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new NotFoundException("User with id " + userId + " not found");
        }
    }

    @Override
    public List<UserDto> getUsers(int from, int size) {
        return userMapper.toDtoList(userRepository.findUsers(from, size));
    }

    @Override
    public List<UserDto> getUsersByIds(List<Long> ids, int from, int size) {
        return userMapper.toDtoList(userRepository.findAllByIds(ids, from, size));
    }
}
