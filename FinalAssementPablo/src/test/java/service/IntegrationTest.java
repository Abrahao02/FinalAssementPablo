package service;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    private static final String BASE_URL = "http://localhost:3000/usuarios"; // Atualize a URL conforme necessário

    @Test
    public void testListarUsuarios() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            // Faz a requisição GET
            connection.setRequestMethod("GET");

            // Obtém o código de resposta
            int responseCode = connection.getResponseCode();

            // Validação: Se o código de resposta é 200, a execução foi bem-sucedida
            assertEquals(200, responseCode, "A listagem de usuários foi bem-sucedida");

            // Se necessário, você pode ler a resposta do servidor
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            // Aqui, você pode adicionar mais verificações com base na resposta do servidor
            // Por exemplo, converter a resposta JSON para objetos Java e verificar seu conteúdo
        } finally {
            connection.disconnect();
        }
    }
}
