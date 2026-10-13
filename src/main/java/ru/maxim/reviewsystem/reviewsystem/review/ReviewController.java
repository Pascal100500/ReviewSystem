package ru.maxim.reviewsystem.reviewsystem.review;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import ru.maxim.reviewsystem.reviewsystem.user.AppUser;
import ru.maxim.reviewsystem.reviewsystem.user.AppUserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final AppUserRepository appUserRepository;

    public ReviewController(
            ReviewRepository reviewRepository,
            AppUserRepository appUserRepository) {
        this.reviewRepository = reviewRepository;
        this.appUserRepository = appUserRepository;
    }

    // Получить все отзывы
    @GetMapping
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAllWithAuthor()
                .stream()
                .map(this::toReviewResponse)
                .toList();
    }

    // Найти отзывы по тексту
    @GetMapping("/search")
    public List<ReviewResponse> searchReviews(
            @RequestParam String query) {

        return reviewRepository.searchWithAuthor(query)
                .stream()
                .map(this::toReviewResponse)
                .toList();
    }
    private ReviewResponse toReviewResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getTitle(),
                review.getContent(),
                review.getCreatedAt(),
                review.getAuthor().getUsername()
        );
    }

    @PostMapping
    public ResponseEntity<Review> createReview(
            @Valid @RequestBody CreateReviewRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        AppUser author = appUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalStateException("Пользователь не найден"));

        Review review = new Review();
        review.setTitle(request.getTitle());
        review.setContent(request.getContent());
        review.setAuthor(author);

        Review savedReview = reviewRepository.save(review);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedReview);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody UpdateReviewRequest request,
            Authentication authentication) {

        Review review = reviewRepository.findByIdWithAuthor(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Отзыв не найден"
                        ));

        String username = authentication.getName();

        if (!review.getAuthor().getUsername().equals(username)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Нельзя редактировать чужой отзыв"
            );
        }

        review.setTitle(request.getTitle());
        review.setContent(request.getContent());

        Review updatedReview = reviewRepository.save(review);

        ReviewResponse response = new ReviewResponse(
                updatedReview.getId(),
                updatedReview.getTitle(),
                updatedReview.getContent(),
                updatedReview.getCreatedAt(),
                username
        );

        return ResponseEntity.ok(response);
    }
}
