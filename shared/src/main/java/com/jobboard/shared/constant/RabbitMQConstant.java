package com.jobboard.shared.constant;

public class RabbitMQConstant {
    public static final String COMPANY_RATING_EXCHANGE = "companyRatingExchange";
    public static final String COMPANY_RATING_EXCHANGE_DLX = "companyRatingExchangeDLX";
    public static final String COMPANY_RATING_ROUTING_KEY = "companyRating";
    public static final String COMPANY_RATING_QUEUE = "companyRatingQueue";
    public static final String COMPANY_RATING_QUEUE_DLQ = "companyRatingQueueDLQ";

    public static final String COMPANY_RATING_QUEUE_DLQ_PARKING_LOT = "companyRatingQueueDLQParkingLot";

    public static final String X_RETRY_HEADER = "x-dlq-retry";
}
