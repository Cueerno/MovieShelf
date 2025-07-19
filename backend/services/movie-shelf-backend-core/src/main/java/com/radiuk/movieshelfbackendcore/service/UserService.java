package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.dto.UserDto;
import com.radiuk.movieshelfbackendcore.dto.UserUpdateDto;
import com.radiuk.movieshelfbackendcore.dto.UserUpdatePasswordDto;
import com.radiuk.movieshelfbackendcore.exception.UserNotUpdatedException;
import com.radiuk.movieshelfbackendcore.mapper.UserMapper;
import com.radiuk.movieshelfbackendcore.model.User;
import com.radiuk.movieshelfbackendcore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserCacheService userCacheService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        return userMapper.userToUserDto(userCacheService.getUserEntity(username));
    }

    @Transactional
    public void update(String username, UserUpdateDto userUpdateDto) {
        User user = userRepository.getByUsername(username);

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
        userCacheService.evictUser(username);
    }

    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
        userCacheService.evictUser(username);
    }

    @Transactional
    public String updateAvatarUrl(String username, MultipartFile file) throws IOException {
        User user = userRepository.getByUsername(username);

        String url = cloudinaryService.uploadAvatar(file, user.getId());

        user.setAvatarUrl(url);
        user.setUpdatedAt(OffsetDateTime.now());
        userRepository.save(user);
        userCacheService.evictUser(username);

        return url;
    }

    @Transactional
    public void updatePassword(UserUpdatePasswordDto userUpdatePasswordDto, String username) {
        User user = userRepository.getByUsername(username);

        if (!passwordEncoder.matches(userUpdatePasswordDto.getOldPassword(), user.getPassword())) {
            throw new UserNotUpdatedException("The old password is incorrect");
        }

        userRepository.updatePassword(user.getUsername(), passwordEncoder.encode(userUpdatePasswordDto.getNewPassword()), OffsetDateTime.now());
    }
}
