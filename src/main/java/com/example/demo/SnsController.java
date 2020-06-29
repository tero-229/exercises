package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sns")
public class SnsController {

    private final SnsMessenger messenger = new SnsMessenger();

    @PostMapping("/send")
    String publishMessage(@RequestBody String msg) {
        return messenger.sendToSns(msg);
    }
}
