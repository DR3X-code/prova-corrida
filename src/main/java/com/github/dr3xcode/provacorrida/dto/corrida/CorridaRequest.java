package com.github.dr3xcode.provacorrida.dto.corrida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CorridaRequest(
        @NotBlank(message = "O nome do evento é obrigatório")
        String nomeEvento,
        @Positive(message = "A distância deve ser um valor positivo")
        double distancia,
        @NotBlank(message = "A data é obrigatória")
        String data,
        @NotBlank(message = "O local é obrigatório")
        String local
) {
}
