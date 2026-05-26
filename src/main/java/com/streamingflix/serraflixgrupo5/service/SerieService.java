package com.streamingflix.serraflixgrupo5.service;

import com.streamingflix.serraflixgrupo5.entity.Categoria;
import com.streamingflix.serraflixgrupo5.entity.Serie;
import com.streamingflix.serraflixgrupo5.repository.CategoriaRepository;
import com.streamingflix.serraflixgrupo5.respository.SerieRepository;
import com.streamingflix.serraflixgrupo5.request.SerieRequest;
import com.streamingflix.serraflixgrupo5.response.SerieResponse;
import com.streamingflix.serraflixgrupo5.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final CategoriaRepository categoriaRepository;

    public SerieService(SerieRepository serieRepository, CategoriaRepository categoriaRepository) {
        this.serieRepository = serieRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<SerieResponse> listarTodos() {
        return serieRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SerieResponse buscarPorId(Long id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Série não encontrada com id: " + id));
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
        serie.setNotaMedia(0.0);

        if (request.getCategoriaIds() != null && !request.getCategoriaIds().isEmpty()) {
            List<Categoria> categorias = categoriaRepository.findAllById(request.getCategoriaIds());
            serie.setCategorias(categorias);
        }

        return toResponse(serieRepository.save(serie));
    }

    @Transactional
    public SerieResponse atualizar(Long id, SerieRequest request) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Série não encontrada com id: " + id));

        serie.setTitulo(request.getTitulo());
        serie.setDescricao(request.getDescricao());
        serie.setTemporadas(request.getTemporadas());
        serie.setEpisodios(request.getEpisodios());
        serie.setDataLancamento(request.getDataLancamento());

        if (request.getCategoriaIds() != null) {
            List<Categoria> categorias = categoriaRepository.findAllById(request.getCategoriaIds());
            serie.setCategorias(categorias);
        }

        return toResponse(serieRepository.save(serie));
    }

    @Transactional
    public void deletar(Long id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Série não encontrada com id: " + id));
        serieRepository.delete(serie);
    }

    public void atualizarNotaMedia(Long serieId, Double novaMedia) {
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new ResourceNotFoundException("Série não encontrada com id: " + serieId));
        serie.setNotaMedia(novaMedia);
        serieRepository.save(serie);
    }

    private SerieResponse toResponse(Serie serie) {
        SerieResponse response = new SerieResponse();
        response.setId(serie.getId());
        response.setTitulo(serie.getTitulo());
        response.setDescricao(serie.getDescricao());
        response.setTemporadas(serie.getTemporadas());
        response.setEpisodios(serie.getEpisodios());
        response.setDataLancamento(serie.getDataLancamento());
        response.setNotaMedia(serie.getNotaMedia());
        response.setCategorias(serie.getCategorias().stream()
                .map(Categoria::getNome)
                .collect(Collectors.toList()));
        return response;
    }
}