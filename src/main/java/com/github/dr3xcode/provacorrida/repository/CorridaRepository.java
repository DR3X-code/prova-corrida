package com.github.dr3xcode.provacorrida.repository;

import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import com.github.dr3xcode.provacorrida.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CorridaRepository extends JpaRepository<CorridaEntity, Long> {

    Optional<CorridaEntity> findByNomeEvento(String nomeEvento);
    List<CorridaEntity> findAllByStatus(StatusEnum status);

    boolean existsByNomeEvento(String nomeEvento);
}
