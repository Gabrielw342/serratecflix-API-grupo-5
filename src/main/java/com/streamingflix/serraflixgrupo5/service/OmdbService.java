package com.streamingflix.serraflixgrupo5.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.streamingflix.serraflixgrupo5.dto.response.OmdbResponseDTO;
import com.streamingflix.serraflixgrupo5.exception.BadRequestException;
import com.streamingflix.serraflixgrupo5.exception.ResourceNotFoundException;

@Service
public class OmdbService {

    private static final String API_KEY = "d3c6e4a6";

    private static final String URL_BASE =
            "https://www.omdbapi.com/";

    private final HttpClient client =
            HttpClient.newHttpClient();

    private final Gson gson =
            new Gson();

    public OmdbResponseDTO buscarFilme(String titulo) {

        try {

            String tituloFormatado =
                    URLEncoder.encode(
                            titulo,
                            StandardCharsets.UTF_8);

            String endereco =
                    URL_BASE
                    + "?apikey="
                    + API_KEY
                    + "&t="
                    + tituloFormatado;

            HttpRequest request =
                    HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            JsonObject objeto =
                    gson.fromJson(json, JsonObject.class);

            
            if (objeto.has("Response")
                    && objeto.get("Response")
                    .getAsString()
                    .equals("False")) {

                throw new ResourceNotFoundException(
                        "Filme não encontrado na API exterha OMDB");
            }

            return gson.fromJson(
                    json,
                    OmdbResponseDTO.class);

        } catch (IOException e) {

            throw new BadRequestException(
                    "Erro de conexão com a OMDb API");

        } catch (InterruptedException e) {

            throw new BadRequestException(
                    "Requisição interrompida");
        }
    }
}