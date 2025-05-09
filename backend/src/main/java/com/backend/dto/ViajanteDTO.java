package com.backend.dto;

import com.backend.model.entity.Viajante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViajanteDTO {

    private long id;
    private String nome;
    private String sobrenome;
    private String dataNascimento;
    private String email;
    private Integer nivelExperiencia;
    private String telefone;

    public static ViajanteDTO create(Viajante viajante) {
        ModelMapper modelMapper = new ModelMapper();
        ViajanteDTO dto = modelMapper.map(viajante, ViajanteDTO.class);
        return dto;
    }
}
