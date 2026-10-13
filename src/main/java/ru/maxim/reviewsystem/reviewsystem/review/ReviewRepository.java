package ru.maxim.reviewsystem.reviewsystem.review;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title, String content
    );

    @Query("SELECT r FROM Review r JOIN FETCH r.author")
    List<Review> findAllWithAuthor();

    @Query("""
       SELECT r FROM Review r
       JOIN FETCH r.author
       WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%'))
          OR LOWER(r.content) LIKE LOWER(CONCAT('%', :query, '%'))
       """)
    List<Review> searchWithAuthor(@Param("query") String query);

    @Query("""
       SELECT r FROM Review r
       JOIN FETCH r.author
       WHERE r.id = :id
       """)
    Optional<Review> findByIdWithAuthor(@Param("id") Long id);
}