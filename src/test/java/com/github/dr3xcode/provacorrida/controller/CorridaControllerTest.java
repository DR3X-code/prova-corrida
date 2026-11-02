package com.github.dr3xcode.provacorrida.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.dr3xcode.provacorrida.dto.corrida.CorridaRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.RankingResponse;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import com.github.dr3xcode.provacorrida.exceptions.BusinessException;
import com.github.dr3xcode.provacorrida.exceptions.GlobalExceptionHandler;
import com.github.dr3xcode.provacorrida.exceptions.NotFoundException;
import com.github.dr3xcode.provacorrida.service.CorridaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CorridaControllerTest {

    @Mock
    private CorridaService corridaService;

    @InjectMocks
    private CorridaController corridaController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(corridaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void shouldCreateCorridaSuccessfully() throws Exception {
        Instant data = Instant.now().plusSeconds(3600);
        CorridaRequest request = new CorridaRequest(DistanciaEnum.CINCO_KM, data, "Parque Ibirapuera");

        mockMvc.perform(post("/api/v1/corridas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(corridaService).cadastrarCorrida(any(CorridaRequest.class));
    }

    @Test
    void shouldReturnBadRequestWhenCorridaCreationFails() throws Exception {
        Instant data = Instant.now().plusSeconds(3600);
        CorridaRequest request = new CorridaRequest(DistanciaEnum.CINCO_KM, data, "Parque Ibirapuera");
        doThrow(new BusinessException("Erro ao cadastrar corrida")).when(corridaService).cadastrarCorrida(any(CorridaRequest.class));

        mockMvc.perform(post("/api/v1/corridas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Erro ao cadastrar corrida"));
    }

    @Test
    void shouldReturnRankingSuccessfully() throws Exception {
        RankingResponse response = new RankingResponse(11L, "Ana", DistanciaEnum.CINCO_KM, "00:30:00");
        when(corridaService.getRanking(1L, DistanciaEnum.CINCO_KM)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/corridas/1/ranking/CINCO_KM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Ana"));
    }

    @Test
    void shouldReturnNotFoundWhenRankingCorridaDoesNotExist() throws Exception {
        when(corridaService.getRanking(99L, DistanciaEnum.DEZ_KM)).thenThrow(new NotFoundException("Corrida não encontrada"));

        mockMvc.perform(get("/api/v1/corridas/99/ranking/DEZ_KM"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Corrida não encontrada"));
    }
}
