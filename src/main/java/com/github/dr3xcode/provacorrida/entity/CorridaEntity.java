package com.github.dr3xcode.provacorrida.entity;

import com.github.dr3xcode.provacorrida.enums.DistanciaEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "corridas")
public class CorridaEntity extends AuditDataEntity {


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DistanciaEnum distancia;

    @Column(nullable = false)
    private Instant data;

    @Column(nullable = false)
    private String local;

    @OneToMany(mappedBy = "corrida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InscricaoEntity> inscricoes;


}
