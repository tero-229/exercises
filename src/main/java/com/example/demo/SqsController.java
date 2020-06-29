package com.example.demo;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class SqsController {
    //private final Logger logger = LoggerFactory.getLogger(SqsController.class);
    private final SqsMessenger messenger = new SqsMessenger();

    @PostMapping("/send")
    String postMessage(@RequestBody String msg) {
        return messenger.sendToSqs(msg);
    }
    @GetMapping("/receive")
    String getMessage() {
        return messenger.receiveFromSqs();
    }

}
