package com.streamingflix.serraflixgrupo5.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.streamingflix.serraflixgrupo5.dto.request.FilmeRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.FilmeResponseDTO;
import com.streamingflix.serraflixgrupo5.dto.response.OmdbResponseDTO;
import com.streamingflix.serraflixgrupo5.entity.Categoria;
import com.streamingflix.serraflixgrupo5.entity.Filme;
import com.streamingflix.serraflixgrupo5.exception.BadRequestException;
import com.streamingflix.serraflixgrupo5.exception.ResourceNotFoundException;
import com.streamingflix.serraflixgrupo5.repository.CategoriaRepository;
import com.streamingflix.serraflixgrupo5.repository.FilmeRepository;
import com.streamingflix.serraflixgrupo5enum.ClassificacaoIndicativa;

@Service
public class FilmeService {

    @Autowired
    private OmdbService omdbService;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public FilmeResponseDTO salvar(FilmeRequestDTO dto) {

        if (dto.getDuracao() <= 0) {
            throw new BadRequestException("ERRO! duração deve ser maior que zero");
        }

        if (dto.getNotaMedia() != null &&
                (dto.getNotaMedia() < 0 || dto.getNotaMedia() > 10)) {
            throw new BadRequestException("ERRO! nota deve estar entre 0 e 10");
        }

        Filme filme = new Filme();

        filme.setTitulo(dto.getTitulo());
        filme.setDescricao(dto.getDescricao());
        filme.setDuracao(dto.getDuracao());
        filme.setDataLancamento(dto.getDataLancamento());
        filme.setClassificacaoIndicativa(dto.getClassificacaoIndicativa());
        filme.setNotaMedia(dto.getNotaMedia());

        if (dto.getCategoriasIds() != null) {
            List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriasIds());
            filme.setCategorias(categorias);
        }

        return converterParaResponse(filmeRepository.save(filme));
    }

    public List<FilmeResponseDTO> listarTodos() {

        List<Filme> filmes = filmeRepository.findAll();
        List<FilmeResponseDTO> resposta = new ArrayList<>();

        for (Filme filme : filmes) {
            resposta.add(converterParaResponse(filme));
        }

        return resposta;
    }

    public FilmeResponseDTO buscarPorId(Long id) {

        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ERRO! Filme não encontrado"));

        return converterParaResponse(filme);
    }

    public FilmeResponseDTO atualizar(Long id, FilmeRequestDTO dto) {

        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ERRO! Filme não encontrado"));

        if (dto.getDuracao() <= 0) {
            throw new BadRequestException("ERRO! duração deve ser maior que zero");
        }

        if (dto.getNotaMedia() != null &&
                (dto.getNotaMedia() < 0 || dto.getNotaMedia() > 10)) {
            throw new BadRequestException("ERRO! nota deve estar entre 0 e 10");
        }

        filme.setTitulo(dto.getTitulo());
        filme.setDescricao(dto.getDescricao());
        filme.setDuracao(dto.getDuracao());
        filme.setDataLancamento(dto.getDataLancamento());
        filme.setClassificacaoIndicativa(dto.getClassificacaoIndicativa());
        filme.setNotaMedia(dto.getNotaMedia());

        if (dto.getCategoriasIds() != null) {
            List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriasIds());
            filme.setCategorias(categorias);
        }

        return converterParaResponse(filmeRepository.save(filme));
    }

    public void deletar(Long id) {

        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ERRO! Filme não encontrado"));

        filmeRepository.delete(filme);
    }

    public List<FilmeResponseDTO> listarPorMaiorNota() {

        List<Filme> filmes = filmeRepository.findAllByOrderByNotaMediaDesc();
        List<FilmeResponseDTO> resposta = new ArrayList<>();

        for (Filme filme : filmes) {
            resposta.add(converterParaResponse(filme));
        }

        return resposta;
    }

    public OmdbResponseDTO buscarNaOmdb(String titulo) {
        return omdbService.buscarFilme(titulo);
    }

    public FilmeResponseDTO importarFilme(String titulo) {

        OmdbResponseDTO omdb = omdbService.buscarFilme(titulo);

        if (omdb == null || omdb.getTitle() == null) {
            throw new BadRequestException("Erro ao importar filme da OMDb");
        }

        Filme filme = new Filme();

        filme.setTitulo(omdb.getTitle());
        filme.setDescricao(omdb.getPlot());

        if (omdb.getRuntime() != null && omdb.getRuntime().contains("min")) {
            try {
                String minutos = omdb.getRuntime().replace("min", "").trim();
                filme.setDuracao(Integer.parseInt(minutos));
            } catch (NumberFormatException e) {
                filme.setDuracao(0);
            }
        } else {
            filme.setDuracao(0);
        }

        try {
            if (omdb.getImdbRating() != null &&
                    !omdb.getImdbRating().equals("N/A")) {
                filme.setNotaMedia(Double.parseDouble(omdb.getImdbRating()));
            }
        } catch (NumberFormatException e) {
            filme.setNotaMedia(0.0);
        }

        filme.setClassificacaoIndicativa(ClassificacaoIndicativa.LIVRE);

        try {
            if (omdb.getYear() != null && omdb.getYear().matches("\\d{4}")) {
                filme.setDataLancamento(java.time.LocalDate.of(
                        Integer.parseInt(omdb.getYear()),
                        1,
                        1
                ));
            } else {
                filme.setDataLancamento(null);
            }
        } catch (Exception e) {
            filme.setDataLancamento(null);
        }

        if (omdb.getGenre() != null && !omdb.getGenre().isEmpty()) {

            String[] generos = omdb.getGenre().split(",");

            List<Categoria> categorias = new ArrayList<>();

            for (String g : generos) {

                String nome = g.trim();

                Categoria categoria = categoriaRepository
                        .findByNome(nome)
                        .orElseGet(() -> {
                            Categoria nova = new Categoria();
                            nova.setNome(nome);
                            return categoriaRepository.save(nova);
                        });

                categorias.add(categoria);
            }

            filme.setCategorias(categorias);
        }

        return converterParaResponse(filmeRepository.save(filme));
    }

    private FilmeResponseDTO converterParaResponse(Filme filme) {

        FilmeResponseDTO dto = new FilmeResponseDTO();

        dto.setId(filme.getId());
        dto.setTitulo(filme.getTitulo());
        dto.setDescricao(filme.getDescricao());
        dto.setDuracao(filme.getDuracao());
        dto.setDataLancamento(filme.getDataLancamento());
        dto.setClassificacaoIndicativa(filme.getClassificacaoIndicativa());
        dto.setNotaMedia(filme.getNotaMedia());

        List<String> categorias = new ArrayList<>();

        if (filme.getCategorias() != null) {
            for (Categoria categoria : filme.getCategorias()) {
                categorias.add(categoria.getNome());
            }
        }

        dto.setCategorias(categorias);

        return dto;
    }
}