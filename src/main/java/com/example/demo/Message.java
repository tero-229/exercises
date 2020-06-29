package com.example.demo;

import java.util.Date;
import java.util.Objects;

public class Message {
    private Date sent;
    private String handle;
    private String message;
    private long id;

    // Static variable which holds latest ID value
    private static long currentId = 0;

    // Increment currentId by 1 and return the result
    public static long getNextId() {
        return ++currentId;
    }

    public Message() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return id == message1.id &&
                Objects.equals(sent, message1.sent) &&
                Objects.equals(handle, message1.handle) &&
                Objects.equals(message, message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sent, handle, message, id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "sent=" + sent +
                ", handle='" + handle + '\'' +
                ", message='" + message + '\'' +
                ", id=" + id +
                '}';
    }
}
