package me.silvernine.tutorial.dto.response;

import java.util.UUID;

public record AgenteResponse(UUID id, String nombre, String instrucciones) {
}
