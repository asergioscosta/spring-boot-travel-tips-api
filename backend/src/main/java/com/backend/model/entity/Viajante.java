package com.backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Viajante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String nome;

    @Column
    @NotNull
    private String sobrenome;

    @Column
    @NotNull
    private String dataNascimento;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private Integer nivelExperiencia;

    @Column
    @NotNull
    private String telefone;
}
