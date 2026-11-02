package com.github.dr3xcode.provacorrida.dto.corrida;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

public record InscricaoRequest(

        @NotNull(message = "O id da corrida é obrigatório")
        Long idCorrida,
        @NotNull(message = "O nome é obrigatório")
        String nome,
        @NotNull(message = "O cpf é obrigatório")
        @CPF(message = "O cpf informado é inválido")
        String cpf,
        @NotNull(message = "A idade é obrigatória")
        @Min(value = 18, message = "A idade mínima para inscrição é 18 anos")
        @Max(value = 60, message = "A idade máxima para inscrição é 60 anos")
        Integer idade
) {
}


