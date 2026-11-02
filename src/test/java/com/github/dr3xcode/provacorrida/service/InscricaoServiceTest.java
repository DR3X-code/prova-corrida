package com.github.dr3xcode.provacorrida.service;

import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoResponse;
import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.entity.InscricaoEntity;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import com.github.dr3xcode.provacorrida.exceptions.BusinessException;
import com.github.dr3xcode.provacorrida.exceptions.NotFoundException;
import com.github.dr3xcode.provacorrida.mapper.InscricaoMapper;
import com.github.dr3xcode.provacorrida.repository.InscricaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscricaoServiceTest {

    @Mock
    private InscricaoMapper inscricaoMapper;

    @Mock
    private InscricaoRepository inscricaoRepository;

    @InjectMocks
    private InscricaoService inscricaoService;

    @Test
    void efetuarInscricao_shouldSaveAndReturnId() {
        InscricaoRequest request = new InscricaoRequest(10L, "Ana Souza", "12345678909", 29);
        InscricaoEntity entity = new InscricaoEntity();
        entity.setId(77L);

        when(inscricaoMapper.toEntity(request)).thenReturn(entity);
        when(inscricaoRepository.save(entity)).thenReturn(entity);

        Long result = inscricaoService.efetuarInscricao(request);

        assertThat(result).isEqualTo(77L);
        verify(inscricaoRepository).save(entity);
    }

    @Test
    void efetuarInscricao_shouldThrowNotFoundWhenSaveFails() {
        InscricaoRequest request = new InscricaoRequest(10L, "Ana Souza", "12345678909", 29);
        InscricaoEntity entity = new InscricaoEntity();

        when(inscricaoMapper.toEntity(request)).thenReturn(entity);
        when(inscricaoRepository.save(entity)).thenThrow(new RuntimeException("erro no banco"));

        assertThatThrownBy(() -> inscricaoService.efetuarInscricao(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Erro ao cadastrar inscrição");
    }

    @Test
    void consultarPorCpf_shouldReturnInscricao() {
        InscricaoEntity entity = new InscricaoEntity();
        entity.setId(5L);
        entity.setNome("Joao");
        entity.setCpf("11144477735");
        entity.setPagamento(true);

        when(inscricaoRepository.findByCpf("11144477735")).thenReturn(Optional.of(entity));
        when(inscricaoMapper.toResponse(entity)).thenReturn(new InscricaoResponse(5L, "Joao", "11144477735", DistanciaEnum.CINCO_KM, true));

        InscricaoResponse response = inscricaoService.consultarPorCpf("11144477735");

        assertThat(response).isNotNull();
        assertThat(response.inscricao()).isEqualTo(5L);
        assertThat(response.cpf()).isEqualTo("11144477735");
    }

    @Test
    void consultarPorCpf_shouldThrowNotFoundWhenNotExists() {
        when(inscricaoRepository.findByCpf("99999999999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inscricaoService.consultarPorCpf("99999999999"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Inscrição não encontrada para o CPF: 99999999999");
    }

    @Test
    void consultarPorInscricao_shouldReturnInscricao() {
        InscricaoEntity entity = new InscricaoEntity();
        entity.setId(8L);
        entity.setNome("Paula");
        entity.setCpf("11144477735");
        entity.setPagamento(false);

        when(inscricaoRepository.findById(8L)).thenReturn(Optional.of(entity));
        when(inscricaoMapper.toResponse(entity)).thenReturn(new InscricaoResponse(8L, "Paula", "11144477735", DistanciaEnum.DEZ_KM, false));

        InscricaoResponse response = inscricaoService.consultarPorInscricao(8L);

        assertThat(response).isNotNull();
        assertThat(response.inscricao()).isEqualTo(8L);
        assertThat(response.nome()).isEqualTo("Paula");
    }

    @Test
    void consultarPorInscricao_shouldThrowNotFoundWhenNotExists() {
        when(inscricaoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inscricaoService.consultarPorInscricao(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Inscrição não encontrada para o ID: 99");
    }

    @Test
    void efetuarPagamento_shouldSetPagamentoTrue() {
        InscricaoEntity entity = new InscricaoEntity();
        entity.setId(3L);
        entity.setPagamento(false);

        when(inscricaoRepository.findById(3L)).thenReturn(Optional.of(entity));
        when(inscricaoRepository.save(entity)).thenReturn(entity);

        inscricaoService.efetuarPagamento(3L);

        assertThat(entity.isPagamento()).isTrue();
        verify(inscricaoRepository).save(entity);
    }

    @Test
    void efetuarPagamento_shouldThrowNotFoundWhenInscricaoDoesNotExist() {
        when(inscricaoRepository.findById(3L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inscricaoService.efetuarPagamento(3L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Inscrição não encontrada para o ID: 3");
    }

    @Test
    void efetuarPagamento_shouldThrowBusinessExceptionWhenSaveFails() {
        InscricaoEntity entity = new InscricaoEntity();
        entity.setId(3L);
        entity.setPagamento(false);

        when(inscricaoRepository.findById(3L)).thenReturn(Optional.of(entity));
        when(inscricaoRepository.save(entity)).thenThrow(new RuntimeException("falha ao salvar"));

        assertThatThrownBy(() -> inscricaoService.efetuarPagamento(3L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Erro ao efetuar pagamento");
    }

    @Test
    void checkout_shouldSetDataTermino() {
        InscricaoEntity entity = new InscricaoEntity();
        entity.setId(4L);
        entity.setDtTermino(null);

        when(inscricaoRepository.findById(4L)).thenReturn(Optional.of(entity));
        when(inscricaoRepository.save(entity)).thenReturn(entity);

        inscricaoService.checkout(4L);

        assertThat(entity.getDtTermino()).isNotNull();
        verify(inscricaoRepository).save(entity);
    }

    @Test
    void checkout_shouldThrowNotFoundWhenInscricaoDoesNotExist() {
        when(inscricaoRepository.findById(4L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inscricaoService.checkout(4L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Inscrição não encontrada para o ID: 4");
    }

    @Test
    void checkout_shouldThrowBusinessExceptionWhenSaveFails() {
        InscricaoEntity entity = new InscricaoEntity();
        entity.setId(4L);

        when(inscricaoRepository.findById(4L)).thenReturn(Optional.of(entity));
        when(inscricaoRepository.save(entity)).thenThrow(new RuntimeException("falha ao salvar"));

        assertThatThrownBy(() -> inscricaoService.checkout(4L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Erro ao efetuar checkout");
    }
}
