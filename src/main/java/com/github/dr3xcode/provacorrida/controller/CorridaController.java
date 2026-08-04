package com.github.dr3xcode.provacorrida.controller;

import com.github.dr3xcode.provacorrida.dto.corrida.CorridaRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.RankingResponse;
import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import com.github.dr3xcode.provacorrida.service.CorridaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/corridas")
public class CorridaController {

    private final CorridaService corridaService;

    @PostMapping
    public  ResponseEntity<Void> cadastrarCorrida(@RequestBody @Valid CorridaRequest corridaRequest) {
        corridaService.cadastrarCorrida(corridaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{idCorrida}/ranking/{distancia}")
    public ResponseEntity<List<RankingResponse>> getRanking(@PathVariable Long idCorrida, @PathVariable DistanciaEnum distancia) {
        return ResponseEntity.ok(corridaService.getRanking(idCorrida,distancia));
    }


}
