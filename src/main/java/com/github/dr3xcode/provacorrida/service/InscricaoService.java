package com.github.dr3xcode.provacorrida.service;

import com.github.dr3xcode.provacorrida.dto.inscricao.InscricaoRequest;
import com.github.dr3xcode.provacorrida.dto.inscricao.InscricaoResponse;
import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.entity.InscricaoEntity;
import com.github.dr3xcode.provacorrida.enums.StatusEnum;
import com.github.dr3xcode.provacorrida.mapper.InscricaoMapper;
import com.github.dr3xcode.provacorrida.repository.CorridaRepository;
import com.github.dr3xcode.provacorrida.repository.InscricaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final CorridaRepository corridaRepository;
    private final InscricaoMapper inscricaoMapper;

    public InscricaoService(InscricaoRepository inscricaoRepository, CorridaRepository corridaRepository, InscricaoMapper inscricaoMapper) {
        this.inscricaoRepository = inscricaoRepository;
        this.corridaRepository = corridaRepository;
        this.inscricaoMapper = inscricaoMapper;
    }


    @Transactional
    public InscricaoResponse realizarInscricao(Long corridaId, InscricaoRequest inscricaoRequest) {
        CorridaEntity corridaEntity = corridaRepository.findById(corridaId).orElseThrow(() -> new RuntimeException("Corrida não encontrada"));

        validarCorridaDisponivel(corridaEntity);

        InscricaoEntity inscricaoEntity = inscricaoMapper.toEntity(inscricaoRequest);

        inscricaoEntity.setCorrida(corridaEntity);
        inscricaoEntity.setStatus(StatusEnum.ATIVO);

        InscricaoEntity inscricaoSalva = inscricaoRepository.save(inscricaoEntity);

        return new InscricaoResponse(
                inscricaoSalva.getId(),
                inscricaoSalva.getNomeCorredor(),
                inscricaoSalva.getCorrida().getNomeEvento()
        );
    }

    private void validarCorridaDisponivel(CorridaEntity corridaEntity) {

        if (corridaEntity.getStatus() == StatusEnum.INATIVO) {
            throw new RuntimeException(
                    "Esta corrida não está disponível para inscrição"
            );
        }
    }
}