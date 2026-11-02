package com.github.dr3xcode.provacorrida.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoResponse;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import com.github.dr3xcode.provacorrida.exceptions.BusinessException;
import com.github.dr3xcode.provacorrida.exceptions.GlobalExceptionHandler;
import com.github.dr3xcode.provacorrida.exceptions.NotFoundException;
import com.github.dr3xcode.provacorrida.service.InscricaoService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class InscricaoControllerTest {

    @Mock
    private InscricaoService inscricaoService;

    @InjectMocks
    private InscricaoController inscricaoController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(inscricaoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void shouldCreateInscricaoSuccessfully() throws Exception {
        InscricaoRequest request = new InscricaoRequest(10L, "Ana Souza", "12345678909", 29);
        when(inscricaoService.efetuarInscricao(request)).thenReturn(77L);

        mockMvc.perform(post("/api/v1/inscricoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("77"));
    }

    @Test
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        String invalidJson = "{\"idCorrida\":null,\"nome\":\"\",\"cpf\":\"123\",\"idade\":17}";

        mockMvc.perform(post("/api/v1/inscricoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldConsultInscricaoByIdSuccessfully() throws Exception {
        InscricaoResponse response = new InscricaoResponse(7L, "Paula", "11144477735", DistanciaEnum.CINCO_KM, true);
        when(inscricaoService.consultarPorInscricao(7L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/inscricoes/numero/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inscricao").value(7))
                .andExpect(jsonPath("$.nome").value("Paula"));
    }

    @Test
    void shouldReturnNotFoundWhenInscricaoByIdDoesNotExist() throws Exception {
        when(inscricaoService.consultarPorInscricao(999L)).thenThrow(new NotFoundException("Inscrição não encontrada para o ID: 999"));

        mockMvc.perform(get("/api/v1/inscricoes/numero/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Inscrição não encontrada para o ID: 999"));
    }

    @Test
    void shouldConsultInscricaoByCpfSuccessfully() throws Exception {
        InscricaoResponse response = new InscricaoResponse(4L, "Maria", "12345678909", DistanciaEnum.DEZ_KM, false);
        when(inscricaoService.consultarPorCpf("12345678909")).thenReturn(response);

        mockMvc.perform(get("/api/v1/inscricoes/cpf/12345678909"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value("12345678909"));
    }

    @Test
    void shouldProcessPagamentoSuccessfully() throws Exception {
        doNothing().when(inscricaoService).efetuarPagamento(5L);

        mockMvc.perform(post("/api/v1/inscricoes/pagamento/5"))
                .andExpect(status().isOk());

        verify(inscricaoService).efetuarPagamento(5L);
    }

    @Test
    void shouldReturnBadRequestWhenPagamentoFails() throws Exception {
        doThrow(new BusinessException("Erro ao efetuar pagamento")).when(inscricaoService).efetuarPagamento(5L);

        mockMvc.perform(post("/api/v1/inscricoes/pagamento/5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Erro ao efetuar pagamento"));
    }

    @Test
    void shouldProcessCheckoutSuccessfully() throws Exception {
        doNothing().when(inscricaoService).checkout(6L);

        mockMvc.perform(post("/api/v1/inscricoes/checkout/6"))
                .andExpect(status().isOk());

        verify(inscricaoService).checkout(6L);
    }
}
