package com.github.dr3xcode.provacorrida.service;

import com.github.dr3xcode.provacorrida.dto.corrida.CorridaRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.CorridaResponse;
import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.enums.StatusEnum;
import com.github.dr3xcode.provacorrida.mapper.CorridaMapper;
import com.github.dr3xcode.provacorrida.repository.CorridaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CorridaService {

    private final CorridaMapper corridaMapper;
    private final CorridaRepository corridaRepository;


    public CorridaService(CorridaMapper corridaMapper, CorridaRepository corridaRepository) {
        this.corridaMapper = corridaMapper;
        this.corridaRepository = corridaRepository;
    }


    @Transactional
    public CorridaResponse cadastrarCorrida(CorridaRequest corridaRequest) {
        validarNomeCorrida(corridaRequest.nomeEvento());
        CorridaEntity corridaEntity = corridaMapper.toEntity(corridaRequest);
        corridaEntity.setStatus(StatusEnum.ATIVO);
        CorridaEntity corridaSalva = corridaRepository.save(corridaEntity);
        return corridaMapper.toResponse(corridaSalva);
    }

    public List<CorridaResponse> buscarTodasCorridas() {

        List<CorridaEntity> corridaEntityList =
                corridaRepository.findAll();

        return corridaMapper.toResponseList(corridaEntityList);
    }

    public CorridaResponse buscarCorridaPorId(Long id) {

        CorridaEntity corridaEntity = corridaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corrida não encontrada"));

        return corridaMapper.toResponse(corridaEntity);
    }

    @Transactional
    public CorridaEntity atualizarCorrida(Long id, CorridaRequest corridaRequest) {
        CorridaEntity corridaExistente = corridaRepository.findById(id).orElse(null);
        if (corridaExistente != null) {
            corridaExistente.setNomeEvento(corridaRequest.nomeEvento());
            corridaExistente.setData(corridaRequest.data());
            corridaExistente.setLocal(corridaRequest.local());
            corridaExistente.setDtAtualizacao(Instant.now());
            return corridaRepository.save(corridaExistente);
        }
        return corridaExistente;
    }

    @Transactional
    public CorridaResponse alterarStatusCorrida(Long id) {

        CorridaEntity corridaEntity = corridaRepository.findById(id).orElseThrow(() -> new RuntimeException("Corrida não encontrada"));

        if (corridaEntity.getStatus() == StatusEnum.ATIVO) {
            corridaEntity.setStatus(StatusEnum.INATIVO);
        } else {
            corridaEntity.setStatus(StatusEnum.ATIVO);
        }
        corridaEntity.setDtAtualizacao(Instant.now());
        CorridaEntity corridaAtualizada = corridaRepository.save(corridaEntity);
        return corridaMapper.toResponse(corridaAtualizada);
    }

    @Transactional
    public void deletarCorrida(Long id) {corridaRepository.deleteById(id);    }


    //regra
    private void validarNomeCorrida(String nomeEvento) {

        boolean corridaJaExiste =
                corridaRepository.existsByNomeEvento(nomeEvento);

        if (corridaJaExiste) {
            throw new RuntimeException("Já existe uma corrida com esse nome.");
        }
    }
}
