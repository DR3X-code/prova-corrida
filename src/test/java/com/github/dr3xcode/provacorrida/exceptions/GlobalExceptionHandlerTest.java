package com.github.dr3xcode.provacorrida.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleBusinessException() {
        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(new BusinessException("Erro de negócio"));

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Erro de negócio");
    }

    @Test
    void shouldHandleNotFoundException() {
        ResponseEntity<ErrorResponse> response = handler.handleNotFoundException(new NotFoundException("Registro não encontrado"));

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Registro não encontrado");
    }

    @Test
    void shouldHandleGenericRuntimeException() {
        ResponseEntity<ErrorResponse> response = handler.handleGenericException(new RuntimeException("Falha inesperada"));

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Contate o administrador do sistema");
    }

    @Test
    void shouldHandleMethodArgumentNotValidException() throws NoSuchMethodException {
        ValidacaoPayload payload = new ValidacaoPayload();
        BindingResult bindingResult = new BeanPropertyBindingResult(payload, "payload");
        bindingResult.rejectValue("nome", "NotBlank", "O nome é obrigatório");

        MethodParameter parameter = new MethodParameter(
                GlobalExceptionHandlerTest.class.getDeclaredMethod("dummyValidationMethod", ValidacaoPayload.class),
                0
        );

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter, bindingResult);
        ResponseEntity<ErrorResponse> response = handler.handleMethodArgumentNotValidException(exception);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().validationErrors()).hasSize(1);
        assertThat(response.getBody().validationErrors().getFirst().field()).isEqualTo("nome");
        assertThat(response.getBody().validationErrors().getFirst().message()).isEqualTo("O nome é obrigatório");
    }

    private void dummyValidationMethod(ValidacaoPayload payload) {
    }

    private static class ValidacaoPayload {
        private String nome;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }
}
