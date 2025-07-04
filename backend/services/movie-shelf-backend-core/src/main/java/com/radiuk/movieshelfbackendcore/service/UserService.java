package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.dto.UserAuthDto;
import com.radiuk.movieshelfbackendcore.dto.UserDto;
import com.radiuk.movieshelfbackendcore.dto.UserRegistrationDto;
import com.radiuk.movieshelfbackendcore.mapper.UserMapper;
import com.radiuk.movieshelfbackendcore.model.User;
import com.radiuk.movieshelfbackendcore.repository.UserRepository;
import com.radiuk.movieshelfbackendcore.security.JwtCore;
import com.radiuk.movieshelfbackendcore.security.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new EntityNotFoundException("User with this username already exists");
        }

        if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new EntityNotFoundException("User with this email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.ROLE_USER);
        user.setRegisteredAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());

        userRepository.save(user);
    }

    @Transactional
    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteByUsername(user.getUsername());
    }

    public String getToken(UserAuthDto userAuthDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAuthDto.getUsername(), userAuthDto.getPassword());
        authenticationManager.authenticate(authenticationToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userAuthDto.getUsername());
        return jwtCore.generateToken(userDetails);
    }

    public UserDetailsImpl getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Transactional
    public void updateAvatarUrl(Long id, String url) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        user.setAvatarUrl(url);
        userRepository.save(user);
    }
}
