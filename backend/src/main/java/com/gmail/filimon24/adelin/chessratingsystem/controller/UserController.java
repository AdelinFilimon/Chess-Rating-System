package com.gmail.filimon24.adelin.chessratingsystem.controller;

import com.gmail.filimon24.adelin.chessratingsystem.business.dto.UserProfileResponse;
import com.gmail.filimon24.adelin.chessratingsystem.business.dto.UserRegistrationRequest;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.business.security.UserDetailsImpl;
import com.gmail.filimon24.adelin.chessratingsystem.business.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        userService.registerUser(userRegistrationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<?> insertUser(@RequestBody User user) {
        userService.insertUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/my-profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetailsImpl principal) {
        UserProfileResponse profile = userService.getProfileByUsername(principal.getUsername());
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/profiles/{username}")
    public ResponseEntity<?> getProfile(@PathVariable String username) {
        UserProfileResponse profile = userService.getProfileByUsername(username);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
