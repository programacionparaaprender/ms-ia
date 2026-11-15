package me.silvernine.tutorial.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CrearAgenteRequest(
        @NotBlank String nombre,
        @NotBlank String instrucciones
) {
}
