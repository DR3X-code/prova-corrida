package com.github.dr3xcode.provacorrida.integration;

import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import com.github.dr3xcode.provacorrida.repository.CorridaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class InscricaoIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CorridaRepository corridaRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldCreateAndQueryInscricaoFlow() throws Exception {
        CorridaEntity corrida = new CorridaEntity();
        corrida.setDistancia(DistanciaEnum.CINCO_KM);
        corrida.setData(Instant.now().plusSeconds(86400));
        corrida.setLocal("Parque Ibirapuera");
        corrida = corridaRepository.save(corrida);

        String inscricaoJson = """
                {
                  "idCorrida": %d,
                  "nome": "Maria Souza",
                  "cpf": "12345678909",
                  "idade": 28
                }
                """.formatted(corrida.getId());

        MvcResult inscricaoResult = mockMvc.perform(post("/api/v1/inscricoes")
                        .contentType(APPLICATION_JSON)
                        .content(inscricaoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNumber())
                .andReturn();

        Long inscricaoId = Long.parseLong(inscricaoResult.getResponse().getContentAsString());

        mockMvc.perform(get("/api/v1/inscricoes/cpf/12345678909"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value("12345678909"))
                .andExpect(jsonPath("$.nome").value("Maria Souza"));

        mockMvc.perform(post("/api/v1/inscricoes/pagamento/{inscricao}", inscricaoId))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/inscricoes/checkout/{inscricao}", inscricaoId))
                .andExpect(status().isOk());
    }
}
