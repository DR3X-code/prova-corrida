package com.github.dr3xcode.provacorrida.repository;

import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.entity.InscricaoEntity;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class InscricaoRepositoryTest {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private CorridaRepository corridaRepository;

    @Test
    void shouldFindByCpf() {
        CorridaEntity corrida = new CorridaEntity();
        corrida.setDistancia(DistanciaEnum.CINCO_KM);
        corrida.setData(Instant.now().plusSeconds(3600));
        corrida.setLocal("Parque Ibirapuera");
        corrida = corridaRepository.save(corrida);

        InscricaoEntity inscricao = new InscricaoEntity();
        inscricao.setCorrida(corrida);
        inscricao.setNome("Ana Souza");
        inscricao.setCpf("12345678909");
        inscricao.setIdade(30);
        inscricao.setPagamento(false);
        inscricaoRepository.save(inscricao);

        Optional<InscricaoEntity> found = inscricaoRepository.findByCpf("12345678909");

        assertThat(found).isPresent();
        assertThat(found.get().getNome()).isEqualTo("Ana Souza");
        assertThat(found.get().getCorrida().getId()).isEqualTo(corrida.getId());
    }

    @Test
    void shouldReturnEmptyWhenCpfDoesNotExist() {
        Optional<InscricaoEntity> found = inscricaoRepository.findByCpf("00000000000");

        assertThat(found).isEmpty();
    }
}
