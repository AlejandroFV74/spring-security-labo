package com.server.app.controllers;

import com.server.app.config.JsonWebToken;
import com.server.app.dto.user.UserCreateDto;
import com.server.app.entities.User;
import com.server.app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JsonWebToken jwtUtil;

    public AuthController(UserService userService, JsonWebToken jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserCreateDto dto) {
        User createdUser = userService.signUp(dto);
        String token = jwtUtil.createToken(createdUser);


        Map<String,Object> response = Map.of(
                "token", token,
                "data",createdUser
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        String token = userService.login(email, password);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/profile")
    public ResponseEntity<User> updateProfile(@AuthenticationPrincipal User user, @RequestBody Map<String, String> body) {
        String newName = body.get("name");
        String newEmail = body.get("email");
        String newSurName = body.get("surname");
        String newUserName = body.get("username");

        User updatedUser = userService.updateProfile(user.getId(), newName, newEmail,newSurName,newUserName);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal User user, @RequestBody Map<String, String> body) {
        String oldPassword = body.get("oldpassword");
        String newPassword = body.get("newpassword");
        String confirmPassword = body.get("confirmpassword");

        userService.updatePassword(user.getId(), oldPassword, newPassword,confirmPassword);
        return ResponseEntity.ok(Map.of("message", "Contraseña modificada con éxito"));
    }


}