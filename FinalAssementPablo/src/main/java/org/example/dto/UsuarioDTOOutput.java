package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTOOutput {
    private int id;
    private String nome;
    // Atributo senha não está incluído nesta classe
}
