package com.github.dr3xcode.provacorrida.mapper;

import com.github.dr3xcode.provacorrida.dto.corrida.CorridaRequest;
import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CorridaMapperTest {

    private final CorridaMapper mapper = Mappers.getMapper(CorridaMapper.class);

    @Test
    void shouldMapRequestToEntity() {
        Instant data = Instant.now().plusSeconds(3600);
        CorridaRequest request = new CorridaRequest(DistanciaEnum.CINCO_KM, data, "Parque do Ibirapuera");

        CorridaEntity entity = mapper.toEntity(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getDistancia()).isEqualTo(DistanciaEnum.CINCO_KM);
        assertThat(entity.getData()).isEqualTo(data);
        assertThat(entity.getLocal()).isEqualTo("Parque do Ibirapuera");
    }
}
