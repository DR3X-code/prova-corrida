package com.github.dr3xcode.provacorrida.mapper;


import com.github.dr3xcode.provacorrida.dto.corrida.CorridaRequest;
import com.github.dr3xcode.provacorrida.entity.CorridaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CorridaMapper {

    CorridaEntity toEntity(CorridaRequest corridaRequest);
}
