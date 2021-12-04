package com.demisco.digishop.repository;

import com.demisco.digishop.model.Role;
import com.demisco.digishop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}