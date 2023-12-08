package org.example.controller;

import org.example.dto.UsuarioDTOInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.UsuarioService;

import static spark.Spark.*;

public class UsuarioController {
    private UsuarioService usuarioService = new UsuarioService();
    private ObjectMapper objectMapper = new ObjectMapper();

    // Método para manipular requisições HTTP simuladas
    public void respostasRequisicoes() {
        // Endpoint para listagem de usuários
        get("/usuarios", (req, res) -> {
            res.type("application/json");

            // Obtemos a lista de usuários do serviço
            var usuariosList = usuarioService.listarUsuarios();

            // Convertendo a lista para JSON usando o ObjectMapper
            String jsonOutput = objectMapper.writeValueAsString(usuariosList);

            // Definindo o código de resposta e retornando o JSON
            res.status(200);
            return jsonOutput;
        });

        // Endpoint para busca de um usuário pelo ID
        get("/usuarios/:id", (req, res) -> {
            res.type("application/json");

            // Obtém o parâmetro ID da URI
            int userId = Integer.parseInt(req.params(":id"));

            // Busca o usuário pelo ID
            var usuarioEncontrado = usuarioService.buscarUsuarioPorId(userId);

            if (usuarioEncontrado.isPresent()) {
                // Se o usuário foi encontrado, converte para JSON e retorna
                res.status(200);
                return objectMapper.writeValueAsString(usuarioEncontrado.get());
            } else {
                // Se o usuário não foi encontrado, retorna um JSON vazio
                res.status(404);
                return "{}";
            }
        });

        // Endpoint para exclusão de um usuário pelo ID
        delete("/usuarios/:id", (req, res) -> {
            // Obtém o parâmetro ID da URI
            int userId = Integer.parseInt(req.params(":id"));

            // Tenta excluir o usuário pelo ID
            usuarioService.excluirUsuario(userId);

            // Define o código de resposta e retorna uma mensagem simples
            res.status(204); // Código 204 significa No Content (sem conteúdo)
            return "Usuário excluído com sucesso!";
        });

        // Endpoint para inserção de um novo usuário
        post("/usuarios", (req, res) -> {
            // Converte o JSON recebido no corpo da requisição para um objeto UsuarioDTOInput
            UsuarioDTOInput novoUsuarioDTO = objectMapper.readValue(req.body(), UsuarioDTOInput.class);

            // Insere o novo usuário
            usuarioService.inserirUsuario(novoUsuarioDTO);

            // Define o código de resposta e retorna uma mensagem simples
            res.status(201); // Código 201 significa Created (criado com sucesso)
            return "Usuário inserido com sucesso!";
        });

        // Endpoint para atualização de um usuário
        put("/usuarios", (req, res) -> {
            // Converte o JSON recebido no corpo da requisição para um objeto UsuarioDTOInput
            UsuarioDTOInput usuarioAtualizadoDTO = objectMapper.readValue(req.body(), UsuarioDTOInput.class);

            // Atualiza o usuário
            usuarioService.alterarUsuario(usuarioAtualizadoDTO);

            // Define o código de resposta e retorna uma mensagem simples
            res.status(200); // Código 200 significa OK
            return "Usuário atualizado com sucesso!";
        });

        // ... (outros endpoints e simulações)
    }

    public static void main(String[] args) {
        UsuarioController usuarioController = new UsuarioController();

        // Iniciando o Spark
        usuarioController.iniciarSpark();
    }

    // Método para iniciar o Spark
    public void iniciarSpark() {
        try {
            // Configurando a porta (por exemplo, 4567)
            System.out.println("Configurando Spark...");
            port(3000);

            // Iniciando as respostas às requisições
            respostasRequisicoes();
            System.out.println("Iniciando Spark...");

            // Adiciona um atraso para aguardar a inicialização do servidor
            Thread.sleep(2000);

        } catch (Exception e) {
            // Trata exceções, se ocorrerem durante a inicialização
            e.printStackTrace();
        }
    }
}
