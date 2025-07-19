package com.radiuk.movieshelfbackendcore.repository;

import com.radiuk.movieshelfbackendcore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    User getByUsername(String username);

    @Modifying
    @Query("""
    update User u
    set u.lastLoginAt = :lastLoginAt
    where u.username = :username
    """)
    void updateLastLogin(String username, OffsetDateTime lastLoginAt);

    @Modifying
    @Query("""
    update User u
    set u.avatarUrl= :avatarUrl, u.updatedAt = :updatedAt
    where u.username = :username
    """)
    void updateAvatarUrl(String username, String avatarUrl, OffsetDateTime updatedAt);

    @Modifying
    @Query("""
    update User u
    set u.password = :password, u.updatedAt = :updatedAt
    where u.username = :username
    """)
    void updatePassword(String username, String password, OffsetDateTime updatedAt);
}