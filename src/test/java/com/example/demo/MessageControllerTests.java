package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MessageControllerTests {
    @Autowired
    MessageController messageController;

    @Test
    public void testPostMessageShouldReturnPostedMessageWithCorrectTimeStamp() {
        final String msgMessage = "viesti";
        final String msgHandle = "nimimerkki";
        final Date msgDate = new Date(0);

        Message msg = new Message();
        msg.setMessage(msgMessage);
        msg.setHandle(msgHandle);
        msg.setSent(msgDate);

        Message retval = messageController.postMessage(msg);

        Assert.assertEquals(retval.getMessage(), msgMessage);
        Assert.assertEquals(retval.getHandle(), msgHandle);

        // Check that timestamp from client (msgDate) was overwritten with server timestamp
        // Server timestamp should be greater than original
        Assert.assertTrue(retval.getSent().getTime() > msgDate.getTime());

        // Server timestamp should be within 5 seconds of current time
        long now = new Date().getTime();

        if (retval.getSent().getTime() - 5000 > now || retval.getSent().getTime() + 5000 < now) {
            Assert.fail("Max tolerance is 5000ms");
        }
    }

    @Test
    public void testPostMessageShouldAddPostedMessageToList() {
        // User reflections to get access to message container
        // First we need to clear all messages before test
        // Then we need to be able to verify messages sent
        List<Message> messagesListInMessageContoller = null;
        try {
            Field f = messageController.getClass().getDeclaredField("messages");
            f.setAccessible(true);
            messagesListInMessageContoller = (List<Message>)f.get(messageController);
            messagesListInMessageContoller.clear();
        } catch (Exception e) {
            Assert.fail("Could not access message container");
        }

        // Create reference messages and send them
        Message reference1 = new Message();
        reference1.setMessage("viesti");
        reference1.setHandle("nimimerkki");

        Message reference2 = new Message();
        reference2.setMessage("toinen viesti");
        reference2.setHandle("toinen nimimerkki");

        messageController.postMessage(reference1);
        messageController.postMessage(reference2);

        // Now, get messages from message container (list inside messageController)
        // First message send should be the first message in list
        Message msg1 = messagesListInMessageContoller.get(0);

        // Second message sent should be the second message in list
        Message msg2 = messagesListInMessageContoller.get(1);

        // Remember to override equals -method in Message class
        // Remember that we CANNOT compare timestamps, since server overwrites it
        // Lets make timestamp null for both
        msg1.setSent(null);
        msg2.setSent(null);
        // We also need not compare id, since it´s set on server side
        msg1.setId(0);
        msg2.setId(0);

        Assert.assertEquals(reference1, msg1);
        Assert.assertEquals(reference2, msg2);
    }

    @Test
    public void testPostMessageShouldSetCorrectIdForMessage() {
        // Get next available id
        long idAtStart = Message.getNextId();

        Message msg = new Message();

        // First id given should be idAtStart + 1, second idAtStart + 2, and so on...
        Assert.assertEquals(messageController.postMessage(msg).getId(), idAtStart + 1);
        Assert.assertEquals(messageController.postMessage(msg).getId(), idAtStart + 2);
        Assert.assertEquals(messageController.postMessage(msg).getId(), idAtStart + 3);
    }

    @Test
    public void testGetMessageShouldReturnAllMessagesFromServer() {
        // Code is almost the same with testPostMessageShouldAddPostedMessageToList()
        // Only difference is that we verify results base on getMessages function and not the list contents
        List<Message> messagesListInMessageContoller = null;

        try {
            Field f = messageController.getClass().getDeclaredField("messages"); //NoSuchFieldException
            f.setAccessible(true);
            messagesListInMessageContoller = (List<Message>)f.get(messageController);
            messagesListInMessageContoller.clear();
        } catch (Exception e) {
            Assert.fail("Could not access message container");
        }


        // Create reference messages and send them
        Message reference1 = new Message();
        reference1.setMessage("viesti");
        reference1.setHandle("nimimerkki");

        Message reference2 = new Message();
        reference2.setMessage("toinen viesti");
        reference2.setHandle("toinen nimimerkki");

        messageController.postMessage(reference1);
        messageController.postMessage(reference2);

        // Now, get messages from getMessage() -method
        List<Message> retval = messageController.getMessages(0);

        // First message send should be the first message in list
        Message msg1 = retval.get(0);

        // Second message sent should be the second message in list
        Message msg2 = retval.get(1);

        // Remember to override equals -method in Message class
        // Remember that we CANNOT compare timestamps, since server overwrites it
        // Lets make timestamp null for both
        msg1.setSent(null);
        msg2.setSent(null);
        // We also need not compare id, since it´s set on server side
        msg1.setId(0);
        msg2.setId(0);

        Assert.assertEquals(reference1, msg1);
        Assert.assertEquals(reference2, msg2);
    }

    @Test
    public void testDeleteMessageShouldDeleteCorrectMessage() {
        Message msg = messageController.postMessage(new Message());

        long id = msg.getId();

        messageController.deleteMessage(String.format("%s", id));

        List<Message> retval = messageController.getMessages(0);

        if (retval.stream().filter(m -> m.getId() == id).findAny().orElse(null) != null) {
            Assert.fail(String.format("Message with id %s should not be in the list", id));
        }
    }

    @Test
    public void testGetMessageShouldReturnCorrectMessage() {
        Message msg = messageController.postMessage(new Message());

        long id = msg.getId();

        Message retval = messageController.getMessage(String.format("%s", id));

        Assert.assertEquals(id, retval.getId());
    }
}
