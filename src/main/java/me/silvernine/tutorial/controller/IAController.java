package me.silvernine.tutorial.controller;

import jakarta.validation.Valid;
import me.silvernine.tutorial.dto.request.ConsultaIA;
import me.silvernine.tutorial.dto.response.ConsultaIAResponse;
import me.silvernine.tutorial.service.AsyncGroqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/ia/v1/ia")
public class IAController {

    private final AsyncGroqService asyncGroqService;

    public IAController(AsyncGroqService asyncGroqService) {
        this.asyncGroqService = asyncGroqService;
    }

    @GetMapping
    public Map<String, String> readRoot() {
        return Map.of(
                "message", "Hello, World!",
                "technology", "Spring Boot",
                "state", "online"
        );
    }

    @GetMapping("/saludar")
    public Map<String, String> saludar(@RequestParam String nombre) {
        return Map.of("message", "¡Hola, " + nombre + ". el motor de la API está listo. !");
    }

    @PostMapping("/preguntar")
    public Mono<ConsultaIAResponse> preguntar(@Valid @RequestBody ConsultaIA consulta) {
        return asyncGroqService.consultar(consulta);
    }
}
