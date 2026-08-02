package com.github.dr3xcode.provacorrida.mapper;

import com.github.dr3xcode.provacorrida.dto.inscricao.InscricaoRequest;
import com.github.dr3xcode.provacorrida.dto.inscricao.InscricaoResponse;
import com.github.dr3xcode.provacorrida.entity.InscricaoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InscricaoMapper {


    InscricaoEntity toEntity(InscricaoRequest inscricaoRequest);

    InscricaoResponse toResponse(InscricaoEntity inscricaoEntity);

    List<InscricaoResponse> toResponseList(List<InscricaoEntity> inscricaoEntityList);
}