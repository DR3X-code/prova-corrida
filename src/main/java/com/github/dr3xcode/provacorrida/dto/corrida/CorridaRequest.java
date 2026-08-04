package com.github.dr3xcode.provacorrida.dto.corrida;

import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CorridaRequest(

        @NotNull(message = "A distância obrigatório")
        DistanciaEnum distancia,
        @NotNull(message = "A data é obrigatória")
        Instant data,
        @NotBlank(message = "O local é obrigatório")
        String local
) {
}
