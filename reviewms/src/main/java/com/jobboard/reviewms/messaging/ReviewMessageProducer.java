package com.jobboard.reviewms.messaging;

import com.jobboard.reviewms.service.ReviewService;
import com.jobboard.shared.dto.ReviewDTO;
import com.jobboard.shared.dto.ReviewMessageDTO;
import com.jobboard.shared.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(ReviewMessageDTO reviewMessage) {

        rabbitTemplate
                .convertAndSend("companyRatingQueue", reviewMessage);
    }
}
