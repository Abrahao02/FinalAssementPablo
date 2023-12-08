package service;

import org.example.dto.UsuarioDTOInput;
import org.example.dto.UsuarioDTOOutput;
import org.example.service.UsuarioService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    @Test
    public void testInserirUsuario() {
        // Cria uma instância da classe UsuarioService
        UsuarioService usuarioService = new UsuarioService();

        // Cria um objeto UsuarioDTOInput para inserção
        UsuarioDTOInput novoUsuarioDTO = new UsuarioDTOInput(1, "comedia", "123");

        // Executa o método inserir da classe UsuarioService
        usuarioService.inserirUsuario(novoUsuarioDTO);

        // Executa o método listar da classe UsuarioService
        List<UsuarioDTOOutput> listaUsuarios = usuarioService.listarUsuarios();

        // Validação: Se o tamanho da lista é igual a 1, a execução foi bem-sucedida
        assertEquals(1, listaUsuarios.size(), "A inserção do usuário não foi bem-sucedida");

        // Outras verificações podem ser feitas conforme necessário
        // Por exemplo, você pode verificar se o usuário inserido está correto
        assertEquals("comedia", listaUsuarios.get(0).getNome(), "O nome do usuário não corresponde");
    }
}