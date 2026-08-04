package com.github.dr3xcode.provacorrida.repository;

import com.github.dr3xcode.provacorrida.entity.InscricaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<InscricaoEntity, Long> {


    Optional<InscricaoEntity> findByCpf(String cpf);
}
