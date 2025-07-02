package com.javarush.jira.login.internal;

import com.javarush.jira.common.BaseRepository;
import com.javarush.jira.common.error.NotFoundException;
import com.javarush.jira.login.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Transactional(readOnly = true)
@CacheConfig(cacheNames = "users")
public interface UserRepository extends BaseRepository<User> {

    @Cacheable(key = "#email", unless = "#result == null")
    //@Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    @Transactional
    @CachePut(key = "#user.email")
    User save(User user);

    default User getExistedByEmail(String email) {
        Optional<User> userOpt = findByEmailIgnoreCase(email);
        System.out.println("🔍 findByEmailIgnoreCase(" + email + ") = " + userOpt);
        return userOpt.orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }
}
