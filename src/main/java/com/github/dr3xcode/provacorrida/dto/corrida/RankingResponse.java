package com.github.dr3xcode.provacorrida.dto.corrida;

import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;

public record RankingResponse(

        Long inscricao,
        String nome,
        DistanciaEnum distancia,
        String tempo

) {
}
