package ru.maxim.reviewsystem.reviewsystem.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        try {
            authService.register(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Пользователь успешно зарегистрирован");

        } catch (IllegalArgumentException exception) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(exception.getMessage());
        }
    }
}
