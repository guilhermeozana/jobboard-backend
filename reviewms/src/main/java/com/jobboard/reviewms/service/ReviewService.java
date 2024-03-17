package com.jobboard.reviewms.service;

import com.jobboard.reviewms.messaging.ReviewMessageProducer;
import com.jobboard.shared.dto.ReviewDTO;
import com.jobboard.shared.dto.ReviewMessageDTO;
import com.jobboard.shared.mapper.GenericMapper;
import com.jobboard.reviewms.model.Review;
import com.jobboard.reviewms.exception.ReviewNotFoundException;
import com.jobboard.reviewms.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final ReviewMessageProducer reviewMessageProducer;


    public List<ReviewDTO> getReviewsByCompanyId(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);

        return reviews.stream()
                .map(review -> GenericMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        return GenericMapper.map(review, ReviewDTO.class);
    }

    public Long createReview(Long companyId, ReviewDTO reviewDTO) {
            reviewDTO.setCompanyId(companyId);

        Review review = reviewRepository.save(GenericMapper.map(reviewDTO, Review.class));

        ReviewMessageDTO reviewMessageDTO = prepareMessageToSend(companyId, review);

        reviewMessageProducer.sendMessage(reviewMessageDTO);

        return review.getId();
    }

    private ReviewMessageDTO prepareMessageToSend(Long companyId, Review review) {
        ReviewMessageDTO reviewMessageDTO = GenericMapper.map(review, ReviewMessageDTO.class);

        Double averageRating = getAverageRatingByCompany(review.getCompanyId());

        reviewMessageDTO.setCompanyRatingUpdated(averageRating);

        return reviewMessageDTO;

    }

    private Double getAverageRatingByCompany(Long companyId) {
        List<ReviewDTO> reviewList = getReviewsByCompanyId(companyId);

        return reviewList.stream().mapToDouble(ReviewDTO::getRating).average().orElse(0.0);
    }

    public void updateReview(Long reviewId, ReviewDTO updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        BeanUtils.copyProperties(updatedReview, review);

        reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        reviewRepository.delete(review);
    }

}
