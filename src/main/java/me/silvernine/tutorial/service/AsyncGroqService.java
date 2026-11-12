package me.silvernine.tutorial.service;

import com.fasterxml.jackson.databind.JsonNode;
import me.silvernine.tutorial.dto.request.ConsultaIA;
import me.silvernine.tutorial.dto.response.ConsultaIAResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class AsyncGroqService {

    private static final String MODEL = "openai/gpt-oss-120b";

    private final WebClient webClient;
    private final String apiKey;

    public AsyncGroqService(@Value("${GROQ_API_KEY:}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .build();
    }

    public Mono<ConsultaIAResponse> consultar(ConsultaIA consulta) {
        if (apiKey.isBlank()) {
            return Mono.error(new IllegalStateException("La variable de entorno GROQ_API_KEY no está configurada."));
        }

        Map<String, Object> requestBody = Map.of(
                "model", MODEL,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", "Eres un experto técnico. Responde en máximo "
                                        + consulta.getPalabrasMaximas()
                                        + " palabras. Traduce al español."
                        ),
                        Map.of("role", "user", "content", consulta.getPregunta())
                ),
                "temperature", 0.3
        );

        return webClient.post()
                .uri("/chat/completions")
                .headers(headers -> headers.setBearerAuth(apiKey))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .switchIfEmpty(Mono.error(new IllegalStateException("Groq devolvió una respuesta vacía.")))
                .flatMap(response -> {
                    JsonNode content = response.path("choices").path(0).path("message").path("content");
                    if (!content.isTextual()) {
                        return Mono.error(new IllegalStateException("La respuesta de Groq no contiene texto."));
                    }
                    return Mono.just(new ConsultaIAResponse(content.asText(), "Procesado con Groq LPU"));
                });
    }
}
