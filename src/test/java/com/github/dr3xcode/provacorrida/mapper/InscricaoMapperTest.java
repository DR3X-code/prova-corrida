package com.github.dr3xcode.provacorrida.mapper;

import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoResponse;
import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.entity.InscricaoEntity;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class InscricaoMapperTest {

    private final InscricaoMapper mapper = Mappers.getMapper(InscricaoMapper.class);

    @Test
    void shouldMapRequestToEntity() {
        InscricaoRequest request = new InscricaoRequest(10L, "Maria Silva", "12345678909", 30);

        InscricaoEntity entity = mapper.toEntity(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getCorrida()).isNotNull();
        assertThat(entity.getCorrida().getId()).isEqualTo(10L);
        assertThat(entity.getNome()).isEqualTo("Maria Silva");
        assertThat(entity.getCpf()).isEqualTo("12345678909");
        assertThat(entity.getIdade()).isEqualTo(30);
    }

    @Test
    void shouldMapEntityToResponse() {
        CorridaEntity corrida = new CorridaEntity();
        corrida.setDistancia(DistanciaEnum.CINCO_KM);

        InscricaoEntity entity = new InscricaoEntity();
        entity.setId(7L);
        entity.setNome("Maria Silva");
        entity.setCpf("12345678909");
        entity.setPagamento(true);
        entity.setCorrida(corrida);

        InscricaoResponse response = mapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.inscricao()).isEqualTo(7L);
        assertThat(response.nome()).isEqualTo("Maria Silva");
        assertThat(response.cpf()).isEqualTo("12345678909");
        assertThat(response.distancia()).isEqualTo(DistanciaEnum.CINCO_KM);
        assertThat(response.statusPagamento()).isTrue();
    }
}
