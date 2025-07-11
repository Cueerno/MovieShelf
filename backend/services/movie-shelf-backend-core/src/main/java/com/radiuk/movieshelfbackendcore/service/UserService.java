package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.dto.UserAuthDto;
import com.radiuk.movieshelfbackendcore.dto.UserDto;
import com.radiuk.movieshelfbackendcore.dto.UserRegistrationDto;
import com.radiuk.movieshelfbackendcore.dto.UserUpdateDto;
import com.radiuk.movieshelfbackendcore.exception.UserNotCreatedException;
import com.radiuk.movieshelfbackendcore.mapper.UserMapper;
import com.radiuk.movieshelfbackendcore.model.User;
import com.radiuk.movieshelfbackendcore.repository.UserRepository;
import com.radiuk.movieshelfbackendcore.security.JwtCore;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final UserDetailsService userDetailsService;
    private final CloudinaryService cloudinaryService;

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        System.out.println(user.getUsername());
        return userMapper.userToUserDto(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public void register(UserRegistrationDto userRegistrationDTO) {
        User user = userMapper.registrationDtoToUser(userRegistrationDTO);

        if (userRepository.existsUserByUsername(user.getUsername())) {
            throw new UserNotCreatedException("User with this username already exists");
        }

        if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new UserNotCreatedException("User with this email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.ROLE_USER);
        user.setRegisteredAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        user.setLastLoginAt(OffsetDateTime.now());

        userRepository.save(user);
    }

    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Transactional
    public void update(String username, UserUpdateDto userUpdateDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }

        user.setUpdatedAt(OffsetDateTime.now());

        userRepository.save(user);
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

    public String getToken(UserAuthDto userAuthDto) {
        User user = userRepository.findByUsername(userAuthDto.getUsername()).orElseThrow(EntityNotFoundException::new);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAuthDto.getUsername(), userAuthDto.getPassword());
        authenticationManager.authenticate(authenticationToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userAuthDto.getUsername());

        user.setLastLoginAt(OffsetDateTime.now());
        userRepository.save(user);

        return jwtCore.generateToken(userDetails);
    }
}
