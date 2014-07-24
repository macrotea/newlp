package com.lesso.newlp.api.v1.notify;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by Sean on 4/12/2014.
 */
@Controller
public class NotifyController {
    @MessageMapping("/notify")
//    @SendToUser
//    @SendTo("/topic/greetings")
    public HelloMessage greeting(HelloMessage message) throws Exception {
//        Thread.sleep(3000); // simulated delay
        return message;
    }

}
