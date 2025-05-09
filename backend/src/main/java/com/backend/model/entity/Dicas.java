package com.backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dicas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String destino;

    @Column
    @NotNull
    private String categorias;

    @Column
    @NotNull
    private String descricao;

    @Column
    @NotNull
    private Float custoMedioDia;

    @Column
    @NotNull
    private Integer avaliacao;

    @ManyToOne
    private Viajante viajante;
}
