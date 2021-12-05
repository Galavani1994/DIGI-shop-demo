package com.demisco.digishop.controller;

import com.demisco.digishop.model.User;
import com.demisco.digishop.repository.UserRepository;
import com.demisco.digishop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    @Secured({"ROLE_ADMIN","ROLE_CUSTOMER"})
    public String getUsers(Model model) {
        model.addAttribute("userList", userService.getAllUser());
        return "person/user_management";
    }

    @PostMapping(value = "/user")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String createUser(User user) {
        userService.save(user);
        return "redirect:/api/users/";
    }

    @PostMapping(value = "/updateUser/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String updateUser(@PathVariable("id") String id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("entity", user);
        return "person/user_management";
    }

    @PostMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @CacheEvict(value = "get_all_user_cahce",allEntries = true)
    public String deleteUser(@PathVariable("id") String id) {
        userService.deleteById(id);
        return "redirect:/api/users/";
    }
}
