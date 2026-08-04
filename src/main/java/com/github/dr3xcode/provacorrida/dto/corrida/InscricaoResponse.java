package com.github.dr3xcode.provacorrida.dto.corrida;

import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;

public record InscricaoResponse(

        Long inscricao,
        String nome,
        String cpf,
        DistanciaEnum distancia,
        boolean statusPagamento
) {
}
