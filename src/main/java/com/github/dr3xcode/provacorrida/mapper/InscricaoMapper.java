package com.github.dr3xcode.provacorrida.mapper;


import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoRequest;
import com.github.dr3xcode.provacorrida.dto.corrida.InscricaoResponse;
import com.github.dr3xcode.provacorrida.entity.InscricaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InscricaoMapper {

    @Mapping(target = "corrida.id", source = "idCorrida")
    InscricaoEntity toEntity(InscricaoRequest inscricaoRequest);

    @Mapping(target = "distancia", source = "corrida.distancia")
    @Mapping(target = "inscricao", source = "id")
    @Mapping(target = "statusPagamento", source = "pagamento")
    InscricaoResponse toResponse(InscricaoEntity inscricaoEntity);

}
