package com.jobboard.companyms.messaging;

import com.jobboard.companyms.service.CompanyService;
import com.jobboard.reviewms.model.Review;
import com.jobboard.shared.dto.ReviewDTO;
import com.jobboard.shared.dto.ReviewMessageDTO;
import com.jobboard.shared.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessageDTO reviewMessage) {
        companyService.updateCompanyRating(reviewMessage);
    }

}
