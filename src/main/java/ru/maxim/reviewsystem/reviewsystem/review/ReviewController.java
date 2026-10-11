package ru.maxim.reviewsystem.reviewsystem.review;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Получить все отзывы
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Найти отзывы по тексту
    @GetMapping("/search")
    public List<Review> searchReviews(@RequestParam String query) {
        return reviewRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                        query, query
                );
    }
}
