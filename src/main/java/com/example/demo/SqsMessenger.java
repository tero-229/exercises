package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

public class SqsMessenger {
    private final Logger logger = LoggerFactory.getLogger(SqsController.class);
    /* private String sqsHandle handler = new sqsHandle; */

    private final String myQueueUrl = "https://sqs.eu-west-1.amazonaws.com/054683117628/Teronqueue";

    private final SqsClient client = SqsClient.builder()
            .region(Region.EU_WEST_1)
            /*.credentialsProvider(ProfileCredentialsProvider.builder()
                    .profileName("academy")
                    .build())*/
            .build();

    String sendToSqs(String message) {
        SendMessageRequest msqReq = SendMessageRequest.builder()
                .queueUrl(myQueueUrl)
                .messageBody(message)
                .build();

        SendMessageResponse msgRes = client.sendMessage(msqReq);

        logger.info("Viesti lähetetty. Id: " + msgRes.messageId());
        return message;
    }

    String receiveFromSqs() {
        ReceiveMessageRequest msgReq = ReceiveMessageRequest.builder()
                .queueUrl(myQueueUrl)
                .maxNumberOfMessages(1)
                .build();

        ReceiveMessageResponse msgRes = client.receiveMessage(msgReq);
        //List<Message> lista = msgReq.messages();

        if (msgRes.messages().isEmpty()) {
            return "[No messages in queue]";
        } else if (msgRes.messages().get(0).body().contains("vittu")) {
            return "[Bad language]";
        }
        else {
            Message sqsMessage = msgRes.messages().get(0);
            deleteMessageFromSqs(sqsMessage.receiptHandle());
            return sqsMessage.body();
        }
        //boolean <List> badlanguage = new
    }

    //String messageReceiptHandle = msgRes.messages().get(0).getReceiptHandle();
    //sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageReceiptHandle));
    private void deleteMessageFromSqs(String sqsHandle) {
        DeleteMessageRequest delReq = DeleteMessageRequest.builder()
                .queueUrl(myQueueUrl)
                .receiptHandle(sqsHandle)
                .build();
        client.deleteMessage(delReq);
    }
}
