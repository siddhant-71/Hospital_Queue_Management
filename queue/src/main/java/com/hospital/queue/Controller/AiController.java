package com.hospital.queue.Controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@CrossOrigin("http://localhost:5173")
public class AiController {

    private final ChatClient chatClient;
    public AiController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }




    @GetMapping("/{prompt}")
    public String chat(@PathVariable("prompt") String promptEntered){
        String input="I'm having or feeling "+promptEntered+" suggest me some home remedies for this and also tell of which disease these are symtons and how danger it is , Just give the remedies first and then the possible disease and how serious is this and dont write anything like Im not a doctor give directly the remedies and without any bold letter and give in the list or bullet points";
        return chatClient.prompt(input).call().content();
    }
}
