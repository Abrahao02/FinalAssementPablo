package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTOInput {
    private int id;
    private String nome;
    private String senha;

    public UsuarioDTOInput(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }
}
