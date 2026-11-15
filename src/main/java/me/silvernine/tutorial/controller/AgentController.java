package me.silvernine.tutorial.controller;

import jakarta.validation.Valid;
import me.silvernine.tutorial.dto.request.ConsultaIA;
import me.silvernine.tutorial.dto.request.CrearAgenteRequest;
import me.silvernine.tutorial.dto.response.AgenteResponse;
import me.silvernine.tutorial.dto.response.ConsultaIAResponse;
import me.silvernine.tutorial.service.AgenteService;
import me.silvernine.tutorial.service.AsyncGroqService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/agent/v1/ia")
public class AgentController {

    private final AsyncGroqService asyncGroqService;
    private final AgenteService agenteService;

    public AgentController(AsyncGroqService asyncGroqService, AgenteService agenteService) {
        this.asyncGroqService = asyncGroqService;
        this.agenteService = agenteService;
    }

    @PostMapping("/agentes")
    public ResponseEntity<AgenteResponse> crearAgente(@Valid @RequestBody CrearAgenteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenteService.crear(request));
    }

    @PostMapping("/agentes/{agenteId}/preguntar")
    public Mono<ConsultaIAResponse> preguntarAgente(
            @PathVariable UUID agenteId,
            @Valid @RequestBody ConsultaIA consulta
    ) {
        AgenteResponse agente = agenteService.buscar(agenteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el agente."));
        return asyncGroqService.consultar(consulta, agente.instrucciones());
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
