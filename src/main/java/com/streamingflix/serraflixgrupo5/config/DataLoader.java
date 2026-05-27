package com.streamingflix.serraflixgrupo5.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.streamingflix.serraflixgrupo5.repository.FilmeRepository;
import com.streamingflix.serraflixgrupo5.repository.SerieRepository;
import com.streamingflix.serraflixgrupo5.service.FilmeService;
import com.streamingflix.serraflixgrupo5.service.SerieService;

@Component
public class DataLoader implements CommandLineRunner {

    private final FilmeService filmeService;
    private final SerieService serieService;
    private final FilmeRepository filmeRepository;
    private final SerieRepository serieRepository;

    public DataLoader(FilmeService filmeService,
                      SerieService serieService,
                      FilmeRepository filmeRepository,
                      SerieRepository serieRepository) {

        this.filmeService = filmeService;
        this.serieService = serieService;
        this.filmeRepository = filmeRepository;
        this.serieRepository = serieRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("tentando fazer a população automática do banco");

        try {

            if (filmeRepository.findByTitulo("Inception").isEmpty()) {
                filmeService.importarFilme("Inception");
            }

            if (filmeRepository.findByTitulo("Matrix").isEmpty()) {
                filmeService.importarFilme("Matrix");
            }

            if (filmeRepository.findByTitulo("Titanic").isEmpty()) {
                filmeService.importarFilme("Titanic");
            }

            if (filmeRepository.findByTitulo("Interstellar").isEmpty()) {
                filmeService.importarFilme("Interstellar");
            }

            if (filmeRepository.findByTitulo("Avatar").isEmpty()) {
                filmeService.importarFilme("Avatar");
            }

            if (serieRepository.findByTitulo("Breaking Bad").isEmpty()) {
                serieService.importarSerie("Breaking Bad");
            }

            if (serieRepository.findByTitulo("Dark").isEmpty()) {
                serieService.importarSerie("Dark");
            }

            if (serieRepository.findByTitulo("Stranger Things").isEmpty()) {
                serieService.importarSerie("Stranger Things");
            }

            System.out.println("Banco populado com sucesso!");

        } catch (Exception e) {

            System.out.println("Erro ao popular banco: " + e.getMessage());
        }
    }
}