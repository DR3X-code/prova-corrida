---
title: "Sistema de Corridas de Rua"
aliases: ["Sistema de Corridas de Rua"]
tags: ["projeto", "requisitos", "modelagem", "mentoria"]
created: 2026-07-21
updated: 2026-07-21
status: active
domain: "projetos-de-pratica"
type: "projeto-pratico"
---

# Sistema de Corridas de Rua

## Visão Geral

sistema para organizar provas, inscrições, kits, tempos de chegada e classificação por categoria.

## Jornada 1: Cadastro de Prova
```
Organizador cadastrou corrida
↓
Organizador informou distância, data e local
↓
Sistema validou dados obrigatórios
↓
Prova foi publicada
↓
Inscrições foram abertas
```

## Jornada 2: Inscrição de Corredor
```
Corredor acessou prova
↓
Corredor informou dados pessoais e categoria
↓
Sistema validou idade e categoria
↓
Inscrição foi criada
↓
Número de peito foi gerado
```

## Jornada 3: Retirada de Kit
```
Corredor chegou ao ponto de retirada
↓
Atendente localizou inscrição
↓
Sistema verificou pagamento
↓
Kit foi entregue
↓
Inscrição foi marcada como kit retirado
```

## Jornada 4: Registro de Chegada
```
Corredor cruzou linha de chegada
↓
Sistema registrou tempo final
↓
Sistema vinculou tempo ao número de peito
↓
Resultado foi salvo
↓
Classificação parcial foi atualizada
```

## Jornada 5: Resultado por Categoria
```
Prova foi encerrada
↓
Sistema agrupou corredores por categoria
↓
Sistema ordenou por tempo
↓
Resultado final foi gerado
↓
Pódio por categoria foi exibido
```

## Relacionado

- [[Arquitetura]]
- [[Context Mapping]]
- [[Domain Discovery]]
- [[Event Storming]]
- [[Histórias de Usuário]]
- [[Modelagem de Domínio]]
- [[Requisitos]]
