package com.demisco.digishop.repository;

import com.demisco.digishop.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsername(String username);

    @CacheEvict(value = "findAllUser",allEntries = true)
    List<User> findAll();

    @Cacheable(value = "checkExistByUsername",key = "{#p0,#p1}")
    @Query("SELECT COUNT(u) FROM User u WHERE u.username =:username and u.id <> coalesce(:id,0) ")
    long checkExistByUsername(@Param("username") String username,@Param("id") Long id);
}