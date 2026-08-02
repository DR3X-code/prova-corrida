package com.github.dr3xcode.provacorrida.dto.corrida;

import com.github.dr3xcode.provacorrida.enums.StatusEnum;

public record CorridaResponse(
        Long id,
        String nomeEvento,
        double distancia,
        String data,
        String local,
        StatusEnum status
) {
}
