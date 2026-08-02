package com.github.dr3xcode.provacorrida.repository;

import com.github.dr3xcode.provacorrida.entity.InscricaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository
        extends JpaRepository<InscricaoEntity, Long> {
}