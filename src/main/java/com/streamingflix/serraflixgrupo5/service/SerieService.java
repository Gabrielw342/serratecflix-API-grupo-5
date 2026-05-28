package com.streamingflix.serraflixgrupo5.service;

import com.streamingflix.serraflixgrupo5.dto.request.SerieRequest;
import com.streamingflix.serraflixgrupo5.dto.response.OmdbResponseDTO;
import com.streamingflix.serraflixgrupo5.dto.response.SerieResponse;
import com.streamingflix.serraflixgrupo5.entity.AvaliacaoSerie;
import com.streamingflix.serraflixgrupo5.entity.Categoria;
import com.streamingflix.serraflixgrupo5.entity.Serie;
import com.streamingflix.serraflixgrupo5.exception.ResourceNotFoundException;
import com.streamingflix.serraflixgrupo5.repository.CategoriaRepository;
import com.streamingflix.serraflixgrupo5.repository.SerieRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final CategoriaRepository categoriaRepository;
    private final OmdbService omdbService;

    public SerieService(SerieRepository serieRepository,
                        CategoriaRepository categoriaRepository,
                        OmdbService omdbService) {
        this.serieRepository = serieRepository;
        this.categoriaRepository = categoriaRepository;
        this.omdbService = omdbService;
    }

    public List<SerieResponse> listarTodos() {
        return serieRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SerieResponse buscarPorId(Long id) {

        Serie serie = serieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Série não encontrada com id: " + id));

        return toResponse(serie);
    }

    @Transactional
    public SerieResponse criar(SerieRequest request) {

        Serie serie = new Serie();

        serie.setTitulo(request.getTitulo());
        serie.setDescricao(request.getDescricao());
        serie.setTemporadas(request.getTemporadas());
        serie.setEpisodios(request.getEpisodios());
        serie.setDataLancamento(request.getDataLancamento());

        if (request.getCategoriasIds() != null
                && !request.getCategoriasIds().isEmpty()) {

            List<Categoria> categorias =
                    categoriaRepository.findAllById(
                            request.getCategoriasIds());

            serie.setCategorias(categorias);
        }

        return toResponse(serieRepository.save(serie));
    }

    @Transactional
    public SerieResponse atualizar(Long id, SerieRequest request) {

        Serie serie = serieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Série não encontrada com id: " + id));

        serie.setTitulo(request.getTitulo());
        serie.setDescricao(request.getDescricao());
        serie.setTemporadas(request.getTemporadas());
        serie.setEpisodios(request.getEpisodios());
        serie.setDataLancamento(request.getDataLancamento());

        if (request.getCategoriasIds() != null) {

            List<Categoria> categorias =
                    categoriaRepository.findAllById(
                            request.getCategoriasIds());

            serie.setCategorias(categorias);
        }

        return toResponse(serieRepository.save(serie));
    }

    @Transactional
    public void deletar(Long id) {

        Serie serie = serieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Série não encontrada com id: " + id));

        serieRepository.delete(serie);
    }

    @Transactional
    public SerieResponse importarSerie(String titulo) {

        OmdbResponseDTO omdb =
                omdbService.buscarSerie(titulo);

        if (omdb == null || omdb.getTitle() == null) {
            throw new ResourceNotFoundException(
                    "Série não encontrada na OMDb");
        }

        Serie serie = new Serie();

        serie.setTitulo(omdb.getTitle());
        serie.setDescricao(omdb.getPlot());

        try {

            if (omdb.getYear() != null) {

                String ano =
                        omdb.getYear().substring(0, 4);

                serie.setDataLancamento(
                        LocalDate.of(
                                Integer.parseInt(ano),
                                1,
                                1
                        )
                );
            }

        } catch (Exception e) {
            serie.setDataLancamento(null);
        }

        if (omdb.getGenre() != null
                && !omdb.getGenre().isEmpty()) {

            List<Categoria> categorias =
                    new ArrayList<>();

            for (String g : omdb.getGenre().split(",")) {

                String nome = g.trim();

                Categoria categoria =
                        categoriaRepository.findByNome(nome)
                                .orElseGet(() -> {

                                    Categoria nova =
                                            new Categoria();

                                    nova.setNome(nome);

                                    return categoriaRepository.save(nova);
                                });

                categorias.add(categoria);
            }

            serie.setCategorias(categorias);
        }

        if (omdb.getTotalSeasons() != null
                && omdb.getTotalSeasons().matches("\\d+")) {

            serie.setTemporadas(
                    Integer.parseInt(
                            omdb.getTotalSeasons()));
        } else {
            serie.setTemporadas(0);
        }

        serie.setEpisodios(0);

        return toResponse(serieRepository.save(serie));
    }

    public Double calcularMediaAvaliacoes(Serie serie) {

        List<AvaliacaoSerie> avaliacoes = serie.getAvaliacoes();

        if (avaliacoes == null || avaliacoes.isEmpty()) {
            return 0.0;
        }

        double soma = 0.0;

        for (AvaliacaoSerie avaliacao : avaliacoes) {
            soma += avaliacao.getNota();
        }

        return soma / avaliacoes.size();
    }

    private SerieResponse toResponse(Serie serie) {

        SerieResponse response = new SerieResponse();

        response.setId(serie.getId());
        response.setTitulo(serie.getTitulo());
        response.setDescricao(serie.getDescricao());
        response.setTemporadas(serie.getTemporadas());
        response.setEpisodios(serie.getEpisodios());
        response.setDataLancamento(serie.getDataLancamento());

        response.setMediaAvaliacoes(
                calcularMediaAvaliacoes(serie)
        );

        response.setCategorias(
                serie.getCategorias()
                        .stream()
                        .map(Categoria::getNome)
                        .collect(Collectors.toList())
        );

        return response;
    }
}