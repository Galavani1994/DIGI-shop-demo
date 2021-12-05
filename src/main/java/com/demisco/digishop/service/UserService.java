package com.demisco.digishop.service;

import com.demisco.digishop.model.Role;
import com.demisco.digishop.model.User;
import com.demisco.digishop.repository.RoleRepository;
import com.demisco.digishop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Cacheable(value = "get_all_user_cahce")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Cacheable(value = "user_cache",key = "{#p0}")
    public User findById(String id) {
        return userRepository.findById(Long.parseLong(id)).get();
    }

    @Transactional

    @Caching(evict = {
            @CacheEvict(value = "user_cache",key = "{#p0.id,#p0.username}"),
            @CacheEvict(value = "get_all_user_cahce",allEntries = true)
    })
    public void save(User entity) {

        validateUniqueUsernmae(entity.getUsername(), entity.getId());
        entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_ADMIN");
        roles.add(role);
        entity.setRoles(roles);
        userRepository.save(entity);
    }

    private void validateUniqueUsernmae(String username, Long id) {
        long count = userRepository.checkExistByUsername(username, id);
        if (count > 0) {
            throw new UsernameNotFoundException("Username was already exist :" + username);
        }
    }

    @Transactional
    public void updateUserById(String id) {
        Optional<User> userOptional = userRepository.findById(Long.parseLong(id));
        User user = userOptional.get();
    }

    @Transactional
    public void deleteById(String id) {
        userRepository.deleteById(Long.parseLong(id));
    }
}
