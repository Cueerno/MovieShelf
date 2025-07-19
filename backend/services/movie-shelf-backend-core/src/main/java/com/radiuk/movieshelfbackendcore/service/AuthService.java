package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.dto.UserAuthDto;
import com.radiuk.movieshelfbackendcore.dto.UserRegistrationDto;
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

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final UserDetailsService userDetailsService;
    private final UserCacheService userCacheService;

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

    public String getToken(UserAuthDto userAuthDto) {
        User user = userRepository.findByUsername(userAuthDto.getUsername()).orElseThrow(()  -> new EntityNotFoundException("User not found"));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAuthDto.getUsername(), userAuthDto.getPassword());
        authenticationManager.authenticate(authenticationToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userAuthDto.getUsername());

        user.setLastLoginAt(OffsetDateTime.now());
        userRepository.save(user);
        userCacheService.addUserToCache(user);

        return jwtCore.generateToken(userDetails);
    }
}
