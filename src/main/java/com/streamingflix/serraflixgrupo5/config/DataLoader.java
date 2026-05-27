package com.streamingflix.serraflixgrupo5.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.streamingflix.serraflixgrupo5.service.FilmeService;
import com.streamingflix.serraflixgrupo5.service.SerieService;

@Component
public class DataLoader implements CommandLineRunner {

    private final FilmeService filmeService;
    private final SerieService serieService;

    public DataLoader(FilmeService filmeService,
                      SerieService serieService) {

        this.filmeService = filmeService;
        this.serieService = serieService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("🚀 Iniciando população automática do banco...");

        try {

            filmeService.importarFilme("Inception");
            filmeService.importarFilme("Matrix");
            filmeService.importarFilme("Titanic");
            filmeService.importarFilme("Interstellar");
            filmeService.importarFilme("Avatar");

            serieService.importarSerie("Breaking Bad");
            serieService.importarSerie("Dark");
            serieService.importarSerie("Stranger Things");

            System.out.println("✅ Banco populado com sucesso!");

        } catch (Exception e) {

            System.out.println("❌ Erro ao popular banco: " + e.getMessage());
        }
    }
}