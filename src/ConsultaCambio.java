import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ConsultaCambio {

    public double obtenerTasaDeCambio(String monedaOrigen, String monedaDestino) {
        URI direccion = URI.create(ConversorDeMoneda.API_URL + monedaOrigen + "/" + monedaDestino);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            Gson gson = new Gson();
            Map<String, Object> data = gson.fromJson(jsonResponse, Map.class);

            if (data.containsKey("result") && data.get("result").equals("success")) {
                if (data.containsKey("conversion_rate")) {
                    return (double) data.get("conversion_rate");
                } else {
                    throw new RuntimeException("No se encontró la tasa de cambio en la respuesta.");
                }
            } else if (data.containsKey("error")) {
                throw new RuntimeException("Error en la consulta a la API: " + data.get("error"));
            } else {
                throw new RuntimeException("Respuesta inesperada de la API.");
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("No se pudo obtener la tasa de cambio.", e);
        }
    }
}
