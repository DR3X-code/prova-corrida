package com.github.dr3xcode.provacorrida.service;

import com.github.dr3xcode.provacorrida.dto.corrida.CorridaRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.RankingResponse;
import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import com.github.dr3xcode.provacorrida.mapper.CorridaMapper;
import com.github.dr3xcode.provacorrida.repository.CorridaRepository;
import com.github.dr3xcode.provacorrida.repository.InscricaoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class CorridaService {

    private final CorridaMapper corridaMapper;
    private final CorridaRepository corridaRepository;



    @Transactional
    public void cadastrarCorrida(CorridaRequest corridaRequest) {
        CorridaEntity corridaEntity = corridaMapper.toEntity(corridaRequest);
        try {
            corridaRepository.save(corridaEntity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar corrida", e);
        }

    }

    public List<RankingResponse> getRanking(Long idCorrida, DistanciaEnum distancia) {
        CorridaEntity corridaEntity = corridaRepository.findByIdAndDistancia(idCorrida, distancia).orElseThrow(() -> new RuntimeException("Corrida não encontrada"));

        return corridaEntity.getInscricoes().stream()
                .sorted((i1, i2) -> i1.getDtTermino().compareTo(i2.getDtTermino()))
                .map(inscricao -> new RankingResponse(
                        inscricao.getId(),
                        inscricao.getNome(),
                        distancia,
                        String.valueOf(getTempo(corridaEntity.getData(), inscricao.getDtTermino()))
                ))
                .toList();
    }

    private double getTempo(Instant dtInicio, Instant dtFim){
        return dtFim.toEpochMilli() - dtInicio.toEpochMilli();
    }
}
