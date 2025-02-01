package com.gasparbarancelli.ollama_demo;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class OllamaDemoApplication {

	private final OllamaChatModel chatModel;

    public OllamaDemoApplication(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public static void main(String[] args) {
		SpringApplication.run(OllamaDemoApplication.class, args);
	}

	@GetMapping
	public String chatbot(@RequestParam("message") String message) {
		var userMessage = new UserMessage(message);
		var prompt = new Prompt(userMessage);
		var response = chatModel.call(prompt);
		return response.getResult().getOutput().getText();
	}

}
