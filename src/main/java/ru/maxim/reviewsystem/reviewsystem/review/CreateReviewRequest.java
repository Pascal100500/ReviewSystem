package ru.maxim.reviewsystem.reviewsystem.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewRequest {

    @NotBlank(message = "Заголовок не должен быть пустым")
    @Size(max = 150, message = "Заголовок не должен превышать 150 символов")
    private String title;

    @NotBlank(message = "Текст отзыва не должен быть пустым")
    @Size(max = 5000, message = "Текст отзыва не должен превышать 5000 символов")
    private String content;
}