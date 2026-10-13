package ru.maxim.reviewsystem.reviewsystem.review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        String authorUsername
) {
}