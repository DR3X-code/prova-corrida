package com.github.dr3xcode.provacorrida.controller;

import com.github.dr3xcode.provacorrida.api.InscricoesApi;
import com.github.dr3xcode.provacorrida.dto.corrida.CorridaRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoResponse;
import com.github.dr3xcode.provacorrida.service.CorridaService;
import com.github.dr3xcode.provacorrida.service.InscricaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/inscricoes")
public class InscricaoController implements InscricoesApi {

    private final InscricaoService inscricaoService;

    @PostMapping
    @Override
    public  ResponseEntity<Long> efetuarInscricao(@RequestBody @Valid InscricaoRequest inscricaoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricaoService.efetuarInscricao(inscricaoRequest));
    }

    @Override
    @GetMapping("/numero/{inscricao}")
    public ResponseEntity<InscricaoResponse> consultarPorInscricao(@PathVariable Long inscricao){
        return ResponseEntity.status(HttpStatus.OK).body(inscricaoService.consultarPorInscricao(inscricao));
    }

    @Override
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<InscricaoResponse> consultarPorCpf(@PathVariable String cpf){
        return ResponseEntity.status(HttpStatus.OK).body(inscricaoService.consultarPorCpf(cpf));
    }

    @Override
    @PostMapping("/pagamento/{inscricao}")
    public  ResponseEntity<Void> efetuarPagamento(@PathVariable Long inscricao) {
        inscricaoService.efetuarPagamento(inscricao);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @PostMapping("/checkout/{inscricao}")
    public  ResponseEntity<Void> checkout(@PathVariable Long inscricao) {
        inscricaoService.checkout(inscricao);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
