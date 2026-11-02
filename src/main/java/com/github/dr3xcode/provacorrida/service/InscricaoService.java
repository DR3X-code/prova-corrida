package com.github.dr3xcode.provacorrida.service;

import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoResponse;
import com.github.dr3xcode.provacorrida.entity.InscricaoEntity;
import com.github.dr3xcode.provacorrida.exceptions.BusinessException;
import com.github.dr3xcode.provacorrida.exceptions.NotFoundException;
import com.github.dr3xcode.provacorrida.mapper.InscricaoMapper;
import com.github.dr3xcode.provacorrida.repository.InscricaoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@AllArgsConstructor
@Service
public class InscricaoService {

    private final InscricaoMapper inscricaoMapper;
    private final InscricaoRepository inscricaoRepository;


    @Transactional
    public Long efetuarInscricao(InscricaoRequest inscricaoRequest) {
        InscricaoEntity inscricaoEntity = inscricaoMapper.toEntity(inscricaoRequest);
        try {
            inscricaoRepository.save(inscricaoEntity);
            return inscricaoEntity.getId();
        } catch (Exception e) {
            throw new NotFoundException("Erro ao cadastrar inscrição");
        }

    }

    public InscricaoResponse consultarPorCpf(String cpf) {
        InscricaoEntity inscricaoEntity = inscricaoRepository.findByCpf(cpf).orElseThrow(() -> new NotFoundException("Inscrição não encontrada para o CPF: " + cpf));return inscricaoMapper.toResponse(inscricaoEntity);}

    public InscricaoResponse consultarPorInscricao(Long inscricao) {
        InscricaoEntity inscricaoEntity = inscricaoRepository.findById(inscricao).orElseThrow(() -> new NotFoundException("Inscrição não encontrada para o ID: " + inscricao));
        return inscricaoMapper.toResponse(inscricaoEntity);
    }

    @Transactional
    public void efetuarPagamento(Long inscricao) {
        InscricaoEntity inscricaoEntity = inscricaoRepository.findById(inscricao).orElseThrow(() -> new NotFoundException("Inscrição não encontrada para o ID: " + inscricao));
        inscricaoEntity.setPagamento(true);
        try {
            inscricaoRepository.save(inscricaoEntity);
        } catch (Exception e) {
            throw new BusinessException("Erro ao efetuar pagamento");
        }
    }

    @Transactional
    public void checkout(Long inscricao) {
        InscricaoEntity inscricaoEntity = inscricaoRepository.findById(inscricao).orElseThrow(() -> new NotFoundException("Inscrição não encontrada para o ID: " + inscricao));
        inscricaoEntity.setDtTermino(Instant.now());
        try {
            inscricaoRepository.save(inscricaoEntity);
        } catch (Exception e) {
            throw new BusinessException("Erro ao efetuar checkout");
        }
    }
}
