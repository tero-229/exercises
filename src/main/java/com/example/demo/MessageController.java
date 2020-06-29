package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
@RequestMapping ("/messages")
@RestController

public class MessageController {
    private CopyOnWriteArrayList<Message> messages = new CopyOnWriteArrayList<>();

    @PostMapping("")
    Message postMessage(@RequestBody Message msg) {
        Message newMessage = new Message();

        newMessage.setId(Message.getNextId());
        newMessage.setSent(new Date());
        newMessage.setHandle(msg.getHandle());
        newMessage.setMessage(msg.getMessage());

        messages.add(newMessage);
        return newMessage;
    }
   /*@GetMapping("")
    List<Message> getMessages() {
       return messages;
        }*/

    @GetMapping("")
    List<Message> getMessages(@RequestParam (defaultValue = "0") long timeFrom) {
            return messages.stream()
                    .filter(m -> m.getSent() != null && m.getSent().getTime() >= timeFrom)
                    .collect(Collectors.toList());
        /*} else {
        List<Message> filteredList;
        filteredList = messages
                .stream()
                .filter(s ->s.getSent().getTime() > ms)
                .collect(Collectors.toList());
        return filteredList;
        }*/
    }
    @GetMapping("/{id}")
    Message getMessage(@PathVariable String id){
        return messages.stream().filter(m -> m.getId() == Long.parseLong(id)).findAny().orElse(null);
    }
    @DeleteMapping("/{id}")
    Message deleteMessage(@PathVariable String id) {
        Message msg = messages.stream().filter(m -> m.getId() == Long.parseLong(id)).findAny().orElse(null);

        if (msg == null) {
            return null;
        }
        messages.remove(msg);
        return msg;
    }
   /* @GetMapping("/messages/timeFrom")
    @ResponseBody
    Message getMessagetimeFrom(@RequestParam String id) {
        Message msg = messages
                .stream().filter(m -> m
                        .getId() == Long
                        .parseLong(id)).findAny().orElse(null);

        if (msg == null) {
            return null;
        }
    }*/
}

