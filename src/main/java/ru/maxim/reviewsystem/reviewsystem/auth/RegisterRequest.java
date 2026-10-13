package ru.maxim.reviewsystem.reviewsystem.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Имя пользователя не должно быть пустым")
    @Size(min = 3, max = 50,
            message = "Имя пользователя должно содержать от 3 до 50 символов")
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 8, max = 100,
            message = "Пароль должен содержать от 8 до 100 символов")
    private String password;
}
