package com.jobboard.reviewms.controller;

import com.jobboard.shared.dto.ReviewDTO;
import com.jobboard.reviewms.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getReviewsByCompanyId(@RequestParam Long companyId){
        return ResponseEntity.ok(reviewService.getReviewsByCompanyId(companyId));
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestParam Long companyId,
                                            @RequestBody ReviewDTO reviewDTO){
            Long reviewId = reviewService.createReview(companyId, reviewDTO);

            return ResponseEntity.created(URI.create("reviews/" + reviewId)).build();
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long reviewId){
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));

    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @RequestBody ReviewDTO reviewDTO){
        reviewService.updateReview(reviewId, reviewDTO);

        return ResponseEntity.ok("Review updated successfully");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok("Review deleted successfully");
    }
}
