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
	public RespostaChat chatbot(@RequestParam("message") String message) {
		long inicio = System.currentTimeMillis();

		var mensagemDoUsuario = new UserMessage(message);
		var prompt = new Prompt(mensagemDoUsuario);
		var respostaDoChat = chatModel.call(prompt);
		var saida = respostaDoChat.getResult().getOutput().getText();

		long fim = System.currentTimeMillis();
		long tempoGastoMs = fim - inicio;

		long minutos = tempoGastoMs / 60000;
		long resto   = tempoGastoMs % 60000;
		long segundos = resto / 1000;
		long milissegundos = resto % 1000;

		String tempoGastoFormatado = String.format(
				"%d minuto(s), %d segundo(s) e %d milissegundo(s)",
				minutos, segundos, milissegundos
		);

		return new RespostaChat(saida, tempoGastoFormatado);
	}

	public record RespostaChat(String texto, String tempoGasto) {}

}
