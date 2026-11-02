package com.github.dr3xcode.provacorrida.api;

import com.github.dr3xcode.provacorrida.dto.corrida.CorridaRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.RankingResponse;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Corrida API", description = "API para gerenciamento de corridas")
public interface CorridaApi {

    @Operation(summary = "Cadastrar Corrida", description = "Cadastra uma nova corrida com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Corrida cadastrada com sucesso"),
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
                                            "  \"message\": \"Erro de validação: campo 'nome' não pode ser vazio\",\n" +
                                            "  \"path\": \"/api/v1/corridas\"\n" +
                                            "}"
                            )
                    }
            )),
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
                                            "  \"path\": \"/api/v1/corridas\"\n" +
                                            "}"
                            )
                    }
            ))
    })
    ResponseEntity<Void> cadastrarCorrida(@RequestBody @Valid CorridaRequest corridaRequest) ;


    @Operation(summary = "Obter Ranking", description = "Obtém o ranking de participantes de uma corrida para uma determinada distância.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking obtido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Corrida não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de corrida não encontrada",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-06-01T12:34:56.789Z\",\n" +
                                                    "  \"status\": 404,\n" +
                                                    "  \"error\": \"Not Found\",\n" +
                                                    "  \"message\": \"Corrida não encontrada\",\n" +
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
    ResponseEntity<List<RankingResponse>> getRanking(@PathVariable Long idCorrida, @PathVariable DistanciaEnum distancia);
}
