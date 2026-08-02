package com.github.dr3xcode.provacorrida.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@Entity
@Table(name = "inscricoes")
@EqualsAndHashCode(callSuper = true)
public class InscricaoEntity extends AuditDataEntity {

    private String nomeCorredor;

    private String cpf;

    @ManyToOne
    @JoinColumn(name = "corrida_id", nullable = false)
    private CorridaEntity corrida;
}
