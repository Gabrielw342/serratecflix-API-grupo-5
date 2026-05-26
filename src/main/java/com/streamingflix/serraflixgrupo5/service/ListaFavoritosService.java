package com.streamingflix.serraflixgrupo5.service;

import com.streamingflix.serraflixgrupo5.dto.request.ListaFavoritosRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.ListaFavoritosResponseDTO;
import com.streamingflix.serraflixgrupo5.entity.ListaFavoritos;
import com.streamingflix.serraflixgrupo5.entity.Usuario;
import com.streamingflix.serraflixgrupo5.repository.ListaFavoritosRepository;
import com.streamingflix.serraflixgrupo5.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListaFavoritosService {

    @Autowired
    private ListaFavoritosRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private com.streamingflix.serraflixgrupo5.repository.FilmeRepository filmeRepository;

    @Autowired
    private com.streamingflix.serraflixgrupo5.repository.SerieRepository serieRepository;

    public ListaFavoritosResponseDTO criarLista(ListaFavoritosRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));

        ListaFavoritos lista = new ListaFavoritos();
        lista.setNomeLista(dto.getNomeLista());
        lista.setPrivada(dto.getPrivada());
        lista.setDataCriacao(LocalDate.now());
        lista.setUsuario(usuario);

        lista = repository.save(lista);
        return converterParaResponseDTO(lista);
    }

    public List<ListaFavoritosResponseDTO> listarTodas() {
        List<ListaFavoritos> listas = repository.findAll();
        return listas.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public ListaFavoritosResponseDTO buscarPorId(Long id) {
        ListaFavoritos lista = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de favoritos não encontrada!"));
        return converterParaResponseDTO(lista);
    }

    public ListaFavoritosResponseDTO atualizar(Long id, ListaFavoritosRequestDTO dto) {
        ListaFavoritos listaExistente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de favoritos não encontrada!"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));

        listaExistente.setNomeLista(dto.getNomeLista());
        listaExistente.setPrivada(dto.getPrivada());
        listaExistente.setUsuario(usuario);

        listaExistente = repository.save(listaExistente);
        return converterParaResponseDTO(listaExistente);
    }

    public void deletar(Long id) {
        ListaFavoritos lista = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de favoritos não encontrada!"));
        repository.delete(lista);
    }

    public List<ListaFavoritosResponseDTO> listarPublicas() {
        List<ListaFavoritos> listas = repository.findByPrivadaFalse();
        return listas.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public ListaFavoritosResponseDTO adicionarFilme(Long listaId, Long filmeId) {
        ListaFavoritos lista = repository.findById(listaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista não encontrada!"));

        com.streamingflix.serraflixgrupo5.entity.Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado!"));

        lista.getFilmes().add(filme);
        lista = repository.save(lista);
        return converterParaResponseDTO(lista);
    }

    public ListaFavoritosResponseDTO adicionarSerie(Long listaId, Long serieId) {
        ListaFavoritos lista = repository.findById(listaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista não encontrada!"));

        com.streamingflix.serraflixgrupo5.entity.Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Série não encontrado!"));

        lista.getSeries().add(serie);
        lista = repository.save(lista);
        return converterParaResponseDTO(lista);
    }

    private ListaFavoritosResponseDTO converterParaResponseDTO(ListaFavoritos lista) {
        ListaFavoritosResponseDTO response = new ListaFavoritosResponseDTO();
        response.setId(lista.getId());
        response.setNomeLista(lista.getNomeLista());
        response.setPrivada(lista.getPrivada());
        response.setDataCriacao(lista.getDataCriacao());
        response.setUsuarioId(lista.getUsuario().getId());
        return response;
    }
}