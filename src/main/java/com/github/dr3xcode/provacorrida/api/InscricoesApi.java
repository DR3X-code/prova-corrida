package com.github.dr3xcode.provacorrida.api;

import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoResponse;
import com.github.dr3xcode.provacorrida.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Inscrições API", description = "API para gerenciamento de inscrições")
public interface InscricoesApi {

    @Operation(summary = "Efetuar Inscrição", description = "Efetua a inscrição de um participante em uma corrida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscrição efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de erro de validação",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 400,\n" +
                                                    "  \"error\": \"Bad Request\",\n" +
                                                    "  \"message\": \"Erro de validação: campo 'cpf' não pode ser vazio\",\n" +
                                                    "  \"validationErrors\": [{\n" +
                                                    "    \"field\": \"cpf\",\n" +
                                                    "    \"message\": \"não pode ser vazio\"\n" +
                                                    "  }]\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de erro interno",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 500,\n" +
                                                    "  \"error\": \"Internal Server Error\",\n" +
                                                    "  \"message\": \"Erro interno do servidor\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    ResponseEntity<Long> efetuarInscricao(@RequestBody @Valid InscricaoRequest inscricaoRequest);

    @Operation(summary = "Consultar Inscrição", description = "Consulta uma inscrição pelo número da inscrição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição consultada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de inscrição não encontrada",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 404,\n" +
                                                    "  \"error\": \"Not Found\",\n" +
                                                    "  \"message\": \"Inscrição não encontrada para o ID: 12\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de erro interno",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 500,\n" +
                                                    "  \"error\": \"Internal Server Error\",\n" +
                                                    "  \"message\": \"Erro interno do servidor\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    ResponseEntity<InscricaoResponse> consultarPorInscricao(@PathVariable Long inscricao);

    @Operation(summary = "Consultar Inscrição por CPF", description = "Consulta uma inscrição pelo CPF do participante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição consultada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada para o CPF",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de inscrição não encontrada",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 404,\n" +
                                                    "  \"error\": \"Not Found\",\n" +
                                                    "  \"message\": \"Inscrição não encontrada para o CPF: 12345678900\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de erro interno",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 500,\n" +
                                                    "  \"error\": \"Internal Server Error\",\n" +
                                                    "  \"message\": \"Erro interno do servidor\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    ResponseEntity<InscricaoResponse> consultarPorCpf(@PathVariable String cpf);

    @Operation(summary = "Efetuar Pagamento", description = "Efetua o pagamento de uma inscrição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento efetuado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pagamento não pode ser processado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de pagamento inválido",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 400,\n" +
                                                    "  \"error\": \"Bad Request\",\n" +
                                                    "  \"message\": \"Erro ao efetuar pagamento\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de inscrição não encontrada",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 404,\n" +
                                                    "  \"error\": \"Not Found\",\n" +
                                                    "  \"message\": \"Inscrição não encontrada para o ID: 12\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de erro interno",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 500,\n" +
                                                    "  \"error\": \"Internal Server Error\",\n" +
                                                    "  \"message\": \"Erro interno do servidor\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    ResponseEntity<Void> efetuarPagamento(@PathVariable Long inscricao);

    @Operation(summary = "Finalizar Checkout", description = "Finaliza o processo de checkout de uma inscrição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checkout finalizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Checkout não pode ser processado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de checkout inválido",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 400,\n" +
                                                    "  \"error\": \"Bad Request\",\n" +
                                                    "  \"message\": \"Erro ao efetuar checkout\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de inscrição não encontrada",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 404,\n" +
                                                    "  \"error\": \"Not Found\",\n" +
                                                    "  \"message\": \"Inscrição não encontrada para o ID: 12\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de erro interno",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 500,\n" +
                                                    "  \"error\": \"Internal Server Error\",\n" +
                                                    "  \"message\": \"Erro interno do servidor\",\n" +
                                                    "  \"validationErrors\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    ResponseEntity<Void> checkout(@PathVariable Long inscricao);
}
