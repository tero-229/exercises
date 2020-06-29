package com.example.demo;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

public class SnsMessenger {
    //private final Logger logger = LoggerFactory.getLogger(SnsMessenger.class);

    private final String  myTopicUrl = "arn:aws:sns:eu-west-1:054683117628:TeronTopic";

    private final SnsClient snsClient = SnsClient.builder()
            .region(Region.EU_WEST_1)
            .build();

    String sendToSns(String message) {
        PublishRequest publishRes = PublishRequest.builder()
                .topicArn(myTopicUrl)
                .message(message)
                .build();

        PublishResponse msgRes = snsClient.publish(publishRes);
        
        //logger.info("Viesti lähetetty. Id: " + msgRes.messageId());
        return message;
    }
}
