package org.example.service;

import org.example.dto.UsuarioDTOInput;
import org.example.dto.UsuarioDTOOutput;
import org.example.model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioService {
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();

    // Método para listar todos os usuários
    public List<UsuarioDTOOutput> listarUsuarios() {
        return listaUsuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .collect(Collectors.toList());
    }

    // Método para inserir um novo usuário a partir de um DTO de entrada
    public void inserirUsuario(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);
        listaUsuarios.add(usuario);
    }

    // Método para alterar um usuário existente a partir de um DTO de entrada
    public void alterarUsuario(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);

        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(u -> u.getId() == usuario.getId())
                .findFirst();

        usuarioExistente.ifPresent(u -> {
            u.setNome(usuario.getNome());
            u.setSenha(usuario.getSenha());
        });
    }

    // Método para buscar um usuário pelo ID e converter para DTO de saída
    public Optional<UsuarioDTOOutput> buscarUsuarioPorId(int id) {
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(usuario -> usuario.getId() == id)
                .findFirst();

        return usuarioExistente.map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class));
    }

    // Método para excluir um usuário pelo ID
    public void excluirUsuario(int id) {
        listaUsuarios.removeIf(usuario -> usuario.getId() == id);
    }
}
