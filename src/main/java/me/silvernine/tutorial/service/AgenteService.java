package me.silvernine.tutorial.service;

import me.silvernine.tutorial.dto.request.CrearAgenteRequest;
import me.silvernine.tutorial.dto.response.AgenteResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class AgenteService {

    private final ConcurrentMap<UUID, AgenteResponse> agentes = new ConcurrentHashMap<>();

    public AgenteResponse crear(CrearAgenteRequest request) {
        AgenteResponse agente = new AgenteResponse(
                UUID.randomUUID(),
                request.nombre(),
                request.instrucciones()
        );
        agentes.put(agente.id(), agente);
        return agente;
    }

    public Optional<AgenteResponse> buscar(UUID id) {
        return Optional.ofNullable(agentes.get(id));
    }
}
