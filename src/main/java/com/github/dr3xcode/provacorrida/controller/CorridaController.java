package com.github.dr3xcode.provacorrida.controller;

import com.github.dr3xcode.provacorrida.api.CorridaApi;
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
public class CorridaController implements CorridaApi {

    private final CorridaService corridaService;

    @PostMapping
    @Override
    public  ResponseEntity<Void> cadastrarCorrida(@RequestBody @Valid CorridaRequest corridaRequest) {
        corridaService.cadastrarCorrida(corridaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{idCorrida}/ranking/{distancia}")
    @Override
    public ResponseEntity<List<RankingResponse>> getRanking(@PathVariable Long idCorrida, @PathVariable DistanciaEnum distancia) {
        return ResponseEntity.ok(corridaService.getRanking(idCorrida,distancia));
    }


}//todo: reflexão sobre o codigo
//todo:comparar com a versão original
//todo:pontos de melhoria
//todo:implementar melhorias e tratativa de execeções
//todo:utilizar o validator em outros campos,para trativa
//todo:melhorar a ordenação e validar o melhor o retorno de tempo
//todo:implementar "Global Exception Handler" -> mensagem informando erro para o usuario  @controllerAdvace


//todo:realizar criação de log de erros e eventos
    //@Slf4j log.error("Erro ao cadastrar corrida", exception); "OK"
    //configurar o Log onde quero e padronizar o log   "OK"
    //Gerar Log de forma estruturada, para que seja possível fazer a análise de forma mais fácil e rápida  "ACHO QUE OKAY"
    //colocar o swagger  "OKAY"

//todo:observabilidade e monitoramento
//observalidade de API, para que seja possível monitorar o desempenho e a disponibilidade da API
//instalar o zip com as coisas loki.tempo.grafana.prometheus

//todo:Cobertura de testes
//importa no pom.xml jacoco
//fazer config
//ele vai criar um step no pipeline para gerar o relatório de cobertura de testes
//configurar o jacoco para gerar o relatório de cobertura de testes e porcentual de cobertura de testes
//todo:implementar testes unitarios e de integração(OK)
//todo:IA fazer o front via Swagger (OK)

//topicos:menssageria/trabalhar com arquivo (leitura/converte/recuperar/validar/armazenar)/tarefas agendadas/segurança/
//projeto a curto prazo
