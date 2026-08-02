package com.github.dr3xcode.provacorrida.dto.inscricao;

import jakarta.validation.constraints.NotBlank;

public record InscricaoRequest(

        @NotBlank(message = "O nome do corredor é obrigatório")
        String nomeCorredor,

        @NotBlank(message = "O CPF é obrigatório")
        String cpf

) {
}