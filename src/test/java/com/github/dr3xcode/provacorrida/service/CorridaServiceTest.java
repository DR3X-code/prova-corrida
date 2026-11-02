package com.github.dr3xcode.provacorrida.service;

import com.github.dr3xcode.provacorrida.dto.corrida.CorridaRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.RankingResponse;
import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.entity.InscricaoEntity;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import com.github.dr3xcode.provacorrida.exceptions.BusinessException;
import com.github.dr3xcode.provacorrida.exceptions.NotFoundException;
import com.github.dr3xcode.provacorrida.mapper.CorridaMapper;
import com.github.dr3xcode.provacorrida.repository.CorridaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorridaServiceTest {

    @Mock
    private CorridaMapper corridaMapper;

    @Mock
    private CorridaRepository corridaRepository;

    @InjectMocks
    private CorridaService corridaService;

    @Test
    void cadastrarCorrida_shouldSaveSuccessfully() {
        Instant data = Instant.now().plusSeconds(3600);
        CorridaRequest request = new CorridaRequest(DistanciaEnum.CINCO_KM, data, "Parque Ibirapuera");
        CorridaEntity entity = new CorridaEntity();
        entity.setLocal("Parque Ibirapuera");
        entity.setDistancia(DistanciaEnum.CINCO_KM);
        entity.setData(data);

        when(corridaMapper.toEntity(request)).thenReturn(entity);
        when(corridaRepository.save(entity)).thenReturn(entity);

        corridaService.cadastrarCorrida(request);

        verify(corridaRepository).save(entity);
    }

    @Test
    void cadastrarCorrida_shouldThrowBusinessExceptionWhenSaveFails() {
        Instant data = Instant.now().plusSeconds(3600);
        CorridaRequest request = new CorridaRequest(DistanciaEnum.CINCO_KM, data, "Parque Ibirapuera");
        CorridaEntity entity = new CorridaEntity();
        entity.setLocal("Parque Ibirapuera");
        entity.setDistancia(DistanciaEnum.CINCO_KM);
        entity.setData(data);

        when(corridaMapper.toEntity(request)).thenReturn(entity);
        when(corridaRepository.save(entity)).thenThrow(new RuntimeException("erro no banco"));

        assertThatThrownBy(() -> corridaService.cadastrarCorrida(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Erro ao cadastrar corrida");
    }

    @Test
    void getRanking_shouldReturnOrderedList() {
        Instant data = Instant.parse("2025-01-01T10:00:00Z");
        CorridaEntity corrida = new CorridaEntity();
        corrida.setId(1L);
        corrida.setData(data);
        corrida.setDistancia(DistanciaEnum.CINCO_KM);

        InscricaoEntity primeiro = new InscricaoEntity();
        primeiro.setId(11L);
        primeiro.setNome("Ana");
        primeiro.setDtTermino(data.plusSeconds(1800));

        InscricaoEntity segundo = new InscricaoEntity();
        segundo.setId(12L);
        segundo.setNome("Beto");
        segundo.setDtTermino(data.plusSeconds(900));

        corrida.setInscricoes(List.of(primeiro, segundo));

        when(corridaRepository.findByIdAndDistancia(1L, DistanciaEnum.CINCO_KM)).thenReturn(Optional.of(corrida));

        List<RankingResponse> ranking = corridaService.getRanking(1L, DistanciaEnum.CINCO_KM);

        assertThat(ranking).hasSize(2);
        assertThat(ranking.get(0).nome()).isEqualTo("Beto");
        assertThat(ranking.get(1).nome()).isEqualTo("Ana");
    }

    @Test
    void getRanking_shouldThrowNotFoundWhenCorridaDoesNotExist() {
        when(corridaRepository.findByIdAndDistancia(99L, DistanciaEnum.DEZ_KM)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> corridaService.getRanking(99L, DistanciaEnum.DEZ_KM))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Corrida não encontrada");
    }
}
