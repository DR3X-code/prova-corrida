package com.github.dr3xcode.provacorrida.integration;

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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CorridaIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldCreateCorridaAndReturnRanking() throws Exception {
        String corridaJson = """
                {
                  "distancia": "CINCO_KM",
                  "data": "2099-01-15T10:00:00Z",
                  "local": "Parque Ibirapuera"
                }
                """;

        mockMvc.perform(post("/api/v1/corridas")
                        .contentType(APPLICATION_JSON)
                        .content(corridaJson))
                .andExpect(status().isCreated());

        String inscricaoJson = """
                {
                  "idCorrida": 1,
                  "nome": "Ana Souza",
                  "cpf": "11144477735",
                  "idade": 29
                }
                """;

        MvcResult inscricaoResult = mockMvc.perform(post("/api/v1/inscricoes")
                        .contentType(APPLICATION_JSON)
                        .content(inscricaoJson))
                .andExpect(status().isCreated())
                .andReturn();

        Long inscricaoId = Long.parseLong(inscricaoResult.getResponse().getContentAsString());

        mockMvc.perform(post("/api/v1/inscricoes/pagamento/{inscricao}", inscricaoId))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/inscricoes/checkout/{inscricao}", inscricaoId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/corridas/1/ranking/CINCO_KM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Ana Souza"));
    }
}
