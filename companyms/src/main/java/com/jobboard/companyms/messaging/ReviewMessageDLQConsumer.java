package com.jobboard.companyms.messaging;

import com.jobboard.shared.dto.ReviewMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReviewMessageDLQConsumer {
    private final RabbitTemplate rabbitTemplate;

    private static final String X_RETRY_HEADER = "x-dlq-retry";

    private static final String DLQ = "companyRatingQueueDLQ";

    private static final String DLQ_PARKING_LOT = "companyRatingQueueDLQParkingLot";

    @RabbitListener(queues = DLQ)
    public void proccess(ReviewMessageDTO reviewMessageDTO, @Headers Map<String, Object> headers) {
        Integer retryHeader = (Integer) headers.get(X_RETRY_HEADER);

        if(retryHeader == null) {
            retryHeader = 0;
        }

        if(retryHeader < 3) {
            Map<String, Object> updatedHeaders = new HashMap<>(headers);

            int tryCount = retryHeader + 1;

            updatedHeaders.put(X_RETRY_HEADER, tryCount);

            //Reprocessing

            final MessagePostProcessor messagePostProcessor = message -> {
                MessageProperties messageProperties = message.getMessageProperties();

                updatedHeaders.forEach(messageProperties::setHeader);

                return message;
            };

            //Resend to DLQ
            rabbitTemplate.convertAndSend(DLQ, reviewMessageDTO, messagePostProcessor);

        } else {

            //Reprocessing failed. Send to Parking Lot.
            rabbitTemplate.convertAndSend(DLQ_PARKING_LOT, reviewMessageDTO);
        }


    }
}

