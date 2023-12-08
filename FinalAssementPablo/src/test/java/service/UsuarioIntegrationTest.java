package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.example.dto.UsuarioDTOInput;
import org.example.dto.UsuarioDTOOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioIntegrationTest {

    private static final String BASE_URL = "http://localhost:3000/usuarios"; // Atualize com a URL da sua API

    @Test
    public void testInserirUsuario() throws IOException {
        // 1. Gere dados do usuário usando a API pública https://randomuser.me/api/
        String dadosUsuarioRandom = obterDadosUsuarioRandom();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode usuarioRandomNode = objectMapper.readTree(dadosUsuarioRandom);
        String nomeUsuario = usuarioRandomNode.get("results").get(0).get("name").get("first").asText();
        String senhaUsuario = "SenhaAleatoria"; // Defina uma senha aleatória

        // 2. Crie um objeto UsuarioDTOInput com os dados do usuário gerado aleatoriamente
        UsuarioDTOInput novoUsuarioDTO = new UsuarioDTOInput(nomeUsuario, senhaUsuario);

        // 3. Execute a requisição POST para a sua API
        int codigoResposta = realizarRequisicaoPost(novoUsuarioDTO);

        // 4. Valide se o código de resposta é 201 (Created)
        assertEquals(201, codigoResposta, "A inserção do usuário não foi bem-sucedida");
    }

    private String obterDadosUsuarioRandom() throws IOException {
        URL url = new URL("https://randomuser.me/api/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }

    private int realizarRequisicaoPost(UsuarioDTOInput usuarioDTOInput) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (var os = connection.getOutputStream()) {
            byte[] input = new ObjectMapper().writeValueAsBytes(usuarioDTOInput);
            os.write(input, 0, input.length);
        }

        return connection.getResponseCode();
    }
}