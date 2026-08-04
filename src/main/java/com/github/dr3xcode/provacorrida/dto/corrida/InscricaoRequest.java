package com.github.dr3xcode.provacorrida.dto.corrida;

public record InscricaoRequest(

        Long idCorrida,
        String nome,
        String cpf,
        int idade
) {
}
