package com.jobboard.reviewms.controller;

import com.jobboard.library.dto.ReviewDTO;
import com.jobboard.reviewms.model.Review;
import com.jobboard.reviewms.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> addReview(@RequestParam Long companyId,
                                            @RequestBody ReviewDTO reviewDTO){
            reviewService.createReview(companyId, reviewDTO);

            return ResponseEntity.ok("Review Created Successfully");
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
