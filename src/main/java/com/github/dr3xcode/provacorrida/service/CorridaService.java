package com.github.dr3xcode.provacorrida.service;

import com.github.dr3xcode.provacorrida.dto.corrida.CorridaRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.RankingResponse;
import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import com.github.dr3xcode.provacorrida.exceptions.BusinessException;
import com.github.dr3xcode.provacorrida.exceptions.NotFoundException;
import com.github.dr3xcode.provacorrida.mapper.CorridaMapper;
import com.github.dr3xcode.provacorrida.repository.CorridaRepository;
import com.github.dr3xcode.provacorrida.repository.InscricaoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
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
            log.atInfo()
                    .addKeyValue("evento", "CORRIDA_CADASTRADA")
                    .addKeyValue("corridaId", corridaEntity.getId())
                    .addKeyValue("distancia", corridaEntity.getDistancia())
                    .addKeyValue("data", corridaEntity.getData())
                    .addKeyValue("local", corridaEntity.getLocal())
                    .log("Corrida cadastrada");
        } catch (Exception e) {
            log.atError()
                    .addKeyValue("evento", "ERRO_CADASTRAR_CORRIDA")
                    .addKeyValue("distancia", corridaEntity.getDistancia())
                    .addKeyValue("data", corridaEntity.getData())
                    .addKeyValue("local", corridaEntity.getLocal())
                    .addKeyValue("erro", e.getMessage())
                    .setCause(e)
                    .log("Erro ao cadastrar corrida");
            throw new BusinessException("Erro ao cadastrar corrida");
        }
    }

    public List<RankingResponse> getRanking(Long idCorrida, DistanciaEnum distancia) {
        CorridaEntity corridaEntity = corridaRepository.findByIdAndDistancia(idCorrida, distancia).orElseThrow(() -> new NotFoundException("Corrida não encontrada"));

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
