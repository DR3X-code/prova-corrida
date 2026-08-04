package com.github.dr3xcode.provacorrida.entity;

import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "inscricoes")
public class InscricaoEntity extends AuditDataEntity {

    @ManyToOne
    @JoinColumn(name = "corrida_id", nullable = false)
    private CorridaEntity corrida;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private int idade;

    @Column(nullable = false)
    private boolean pagamento;

    @Column(name = "dt_termino")
    private Instant dtTermino;



}
