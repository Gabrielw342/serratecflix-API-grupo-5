package com.streamingflix.serraflixgrupo5.integration;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class OmdbClient {

    private static final String API_KEY = "SUA_API_KEY";

    private static final String URL_BASE = "https://www.omdbapi.com/";

    private final HttpClient client;
    private final Gson gson;

    public OmdbClient() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public void buscarFilme(String titulo) {

        try {

            String tituloFormatado =
                    URLEncoder.encode(titulo, StandardCharsets.UTF_8);

            
            String endereco =
                    URL_BASE + "?apikey=" + API_KEY + "&t=" + tituloFormatado;

           
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            JsonObject objeto = gson.fromJson(json, JsonObject.class);

            if (objeto.has("Response")
                    && objeto.get("Response").getAsString().equals("False")) {

                System.out.println("Filme não encontrado!");
                return;
            }

            System.out.println("=================================");
            System.out.println("FILME ENCONTRADO!");
            System.out.println("=================================");

            System.out.println("Título: "
                    + objeto.get("Title").getAsString());

            System.out.println("Ano: "
                    + objeto.get("Year").getAsString());

            System.out.println("Diretor: "
                    + objeto.get("Director").getAsString());

            System.out.println("IMDb: "
                    + objeto.get("imdbRating").getAsString());

            System.out.println("Gênero: "
                    + objeto.get("Genre").getAsString());

            System.out.println("Plot: "
                    + objeto.get("Plot").getAsString());

        } catch (IOException e) {

            System.out.println("Erro de conexão com a API!");
            e.printStackTrace();

        } catch (InterruptedException e) {

            System.out.println("Requisição interrompida!");
            e.printStackTrace();

        } catch (Exception e) {

            System.out.println("Erro ao processar os dados!");
            e.printStackTrace();
        }
    }
   
    public static void main(String[] args) {

        OmdbClient client = new OmdbClient();

        client.buscarFilme("Interstellar");
    }
}