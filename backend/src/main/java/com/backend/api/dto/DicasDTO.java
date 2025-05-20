package com.backend.dto;

import com.backend.model.entity.Dicas;
import com.backend.model.entity.Viajante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DicasDTO {

    private Long id;
    private String destino;
    private String categorias;
    private String descricao;
    private Float custoMedioDia;
    private Integer avaliacao;

    private Viajante idViajante;

    public static DicasDTO create(Dicas dicas) {
        ModelMapper modelMapper = new ModelMapper();
        DicasDTO dto = modelMapper.map(dicas, DicasDTO.class);
        return dto;
    }
}
