package com.github.dr3xcode.provacorrida.controller;

import com.github.dr3xcode.provacorrida.dto.CorridaRequest;
import com.github.dr3xcode.provacorrida.dto.CorridaResponse;
import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.enums.StatusEnum;
import com.github.dr3xcode.provacorrida.service.CorridaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corridas")
public class CadastroCorridaController {

    private final CorridaService corridaService;

    public CadastroCorridaController(CorridaService corridaService) {
        this.corridaService = corridaService;
    }

    @PostMapping
    public ResponseEntity<CorridaResponse> cadastrarCorrida(@RequestBody @Valid CorridaRequest corridaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(corridaService.cadastrarCorrida(corridaRequest));
    }

    @GetMapping("/listarCorridas")
    public ResponseEntity<List<CorridaResponse>> buscarTodasCorridas() {
        return ResponseEntity.ok(corridaService.buscarTodasCorridas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorridaResponse> buscarCorridaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(corridaService.buscarCorridaPorId(id));
    }

    //Alterar corrida resquest para corrida entity
    @PutMapping("/atualizarCorrida/{id}")
    public ResponseEntity<CorridaEntity> atualizarCorrida(@PathVariable Long id, @RequestBody @Valid CorridaRequest corridaRequest) {
        return ResponseEntity.ok(corridaService.atualizarCorrida(id, corridaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCorrida(@PathVariable Long id) {corridaService.deletarCorrida(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CorridaResponse> alterarStatusCorrida(@PathVariable Long id) {
        return ResponseEntity.ok(corridaService.alterarStatusCorrida(id));
    }

}
