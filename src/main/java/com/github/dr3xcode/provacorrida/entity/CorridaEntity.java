package com.github.dr3xcode.provacorrida.entity;

import com.github.dr3xcode.provacorrida.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "corridas")
public class CorridaEntity extends AuditDataEntity {

    @Column(nullable = false)
    private String nomeEvento;

    @Column(nullable = false)
    private Double distancia;

    @Column(nullable = false)
    private String data;

    @Column(nullable = false)
    private String local;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    StatusEnum status;
}
