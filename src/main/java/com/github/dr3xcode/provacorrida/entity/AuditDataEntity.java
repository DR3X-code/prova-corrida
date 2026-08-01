package com.github.dr3xcode.provacorrida.entity;

import com.github.dr3xcode.provacorrida.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@MappedSuperclass
@Data
public class AuditDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dt_criacao", updatable = false)
    private Instant dtCriacao;
    @Column(name = "dt_atualizacao")
    private  Instant dtAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private StatusEnum status;

    public AuditDataEntity (){
        this.status = StatusEnum.ATIVO;
        this.dtCriacao = Instant.now();

    }
}
