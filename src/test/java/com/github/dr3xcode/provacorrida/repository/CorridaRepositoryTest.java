package com.github.dr3xcode.provacorrida.repository;

import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
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
class CorridaRepositoryTest {

    @Autowired
    private CorridaRepository corridaRepository;

    @Test
    void shouldFindByIdAndDistancia() {
        CorridaEntity corrida = new CorridaEntity();
        corrida.setDistancia(DistanciaEnum.CINCO_KM);
        corrida.setData(Instant.now().plusSeconds(7200));
        corrida.setLocal("Parque do Ibirapuera");
        corrida = corridaRepository.save(corrida);

        Optional<CorridaEntity> found = corridaRepository.findByIdAndDistancia(corrida.getId(), DistanciaEnum.CINCO_KM);

        assertThat(found).isPresent();
        assertThat(found.get().getLocal()).isEqualTo("Parque do Ibirapuera");
        assertThat(found.get().getDistancia()).isEqualTo(DistanciaEnum.CINCO_KM);
    }

    @Test
    void shouldReturnEmptyWhenDistanceDoesNotMatch() {
        CorridaEntity corrida = new CorridaEntity();
        corrida.setDistancia(DistanciaEnum.DEZ_KM);
        corrida.setData(Instant.now().plusSeconds(3600));
        corrida.setLocal("Avenida Paulista");
        corridaRepository.save(corrida);

        Optional<CorridaEntity> found = corridaRepository.findByIdAndDistancia(corrida.getId(), DistanciaEnum.CINCO_KM);

        assertThat(found).isEmpty();
    }
}
