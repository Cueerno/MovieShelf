package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.model.User;
import com.radiuk.movieshelfbackendcore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCacheService {

    private final UserRepository userRepository;

    @CachePut(value = "users", key = "#result.username")
    public User addUserToCache(User user) {
        System.out.println("Caching new user " + user.getUsername());
        return user;
    }

    @Cacheable(value = "users", key = "#username")
    public User getUserFromCache(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @CacheEvict(value = "users", key = "#username")
    public void evictUserFromCache(String username) {
        System.out.println("Evicting user " + username);
    }
}
