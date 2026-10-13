package ru.maxim.reviewsystem.reviewsystem.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maxim.reviewsystem.reviewsystem.user.AppUser;
import ru.maxim.reviewsystem.reviewsystem.user.AppUserRepository;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder) {

        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {

        if (appUserRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException(
                    "Пользователь с таким именем уже существует"
            );
        }

        AppUser user = new AppUser();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        appUserRepository.save(user);
    }
}