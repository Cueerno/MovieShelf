package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.dto.UserDto;
import com.radiuk.movieshelfbackendcore.dto.UserUpdateDto;
import com.radiuk.movieshelfbackendcore.exception.UserNotUpdatedException;
import com.radiuk.movieshelfbackendcore.mapper.UserMapper;
import com.radiuk.movieshelfbackendcore.model.User;
import com.radiuk.movieshelfbackendcore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CloudinaryService cloudinaryService;

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        System.out.println(user.getUsername());
        return userMapper.userToUserDto(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void update(String username, UserUpdateDto userUpdateDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }
        else if (userRepository.existsUserByUsername(user.getUsername())) {
            throw new UserNotUpdatedException("User with this username already exists");
        }

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        else if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new UserNotUpdatedException("User with this email already exists");
        }

        user.setUpdatedAt(OffsetDateTime.now());

        userRepository.save(user);
    }

    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Transactional
    public String updateAvatarUrl(String username, MultipartFile file) throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        String url = cloudinaryService.uploadAvatar(file, user.getId());
        System.out.println(url);

        user.setAvatarUrl(url);
        user.setUpdatedAt(OffsetDateTime.now());
        userRepository.save(user);

        return url;
    }
}
