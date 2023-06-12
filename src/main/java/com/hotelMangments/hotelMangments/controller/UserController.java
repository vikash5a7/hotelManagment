package com.hotelMangments.hotelMangments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelMangments.hotelMangments.entity.User;
import com.hotelMangments.hotelMangments.repository.UserRepository;
import com.hotelMangments.hotelMangments.request.ErrorDetails;
import com.hotelMangments.hotelMangments.request.LoginRequest;
import com.hotelMangments.hotelMangments.request.SignUpRequest;
import com.hotelMangments.hotelMangments.request.UserRequest;
import com.hotelMangments.hotelMangments.response.JwtAuthenticationResponse;
import com.hotelMangments.hotelMangments.security.JwtTokenProvider;
import com.hotelMangments.hotelMangments.servicesImpl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, ObjectMapper objectMapper, UserRepository userRepository) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        userService.registerUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            String token = jwtTokenProvider.generateToken(authentication.getName(), authentication.getAuthorities());
            JwtAuthenticationResponse response = new JwtAuthenticationResponse(token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            ErrorDetails errorResponse = new ErrorDetails(new Date(), "Invalid email or password", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        User updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
