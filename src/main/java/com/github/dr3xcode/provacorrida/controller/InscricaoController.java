package com.github.dr3xcode.provacorrida.controller;

import com.github.dr3xcode.provacorrida.dto.inscricao.InscricaoRequest;
import com.github.dr3xcode.provacorrida.dto.inscricao.InscricaoResponse;
import com.github.dr3xcode.provacorrida.service.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @PostMapping("/corridas/{corridaId}")
    public ResponseEntity<InscricaoResponse> realizarInscricao(@PathVariable Long corridaId, @RequestBody @Valid InscricaoRequest inscricaoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricaoService.realizarInscricao(corridaId, inscricaoRequest));
    }
}