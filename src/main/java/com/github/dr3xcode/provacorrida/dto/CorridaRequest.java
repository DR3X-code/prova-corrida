package com.github.dr3xcode.provacorrida.dto;

public record CorridaRequest(
        String nomeEvento,
        double distancia,
        String data,
        String local
) {
}
