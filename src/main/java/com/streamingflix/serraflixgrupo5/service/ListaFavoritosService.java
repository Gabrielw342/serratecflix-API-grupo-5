package com.streamingflix.serraflixgrupo5.service;

import com.streamingflix.serraflixgrupo5.dto.request.ListaFavoritosRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.ListaFavoritosResponseDTO;
import com.streamingflix.serraflixgrupo5.entity.ListaFavoritos;
import com.streamingflix.serraflixgrupo5.entity.Usuario;
import com.streamingflix.serraflixgrupo5.exception.ResourceNotFoundException;
import com.streamingflix.serraflixgrupo5.repository.FilmeRepository;
import com.streamingflix.serraflixgrupo5.repository.ListaFavoritosRepository;
import com.streamingflix.serraflixgrupo5.repository.SerieRepository;
import com.streamingflix.serraflixgrupo5.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListaFavoritosService {

    @Autowired
    private ListaFavoritosRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private SerieRepository serieRepository;

    public ListaFavoritosResponseDTO criarLista(ListaFavoritosRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + dto.getUsuarioId()));

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
                .orElseThrow(() -> new ResourceNotFoundException("Lista de favoritos não encontrada com id: " + id));
        return converterParaResponseDTO(lista);
    }

    public ListaFavoritosResponseDTO atualizar(Long id, ListaFavoritosRequestDTO dto) {
        ListaFavoritos listaExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista de favoritos não encontrada com id: " + id));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + dto.getUsuarioId()));

        listaExistente.setNomeLista(dto.getNomeLista());
        listaExistente.setPrivada(dto.getPrivada());
        listaExistente.setUsuario(usuario);

        listaExistente = repository.save(listaExistente);
        return converterParaResponseDTO(listaExistente);
    }

    public void deletar(Long id) {
        ListaFavoritos lista = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista de favoritos não encontrada com id: " + id));
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
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada com id: " + listaId));

        com.streamingflix.serraflixgrupo5.entity.Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com id: " + filmeId));

        lista.getFilmes().add(filme);
        lista = repository.save(lista);
        return converterParaResponseDTO(lista);
    }

    public ListaFavoritosResponseDTO adicionarSerie(Long listaId, Long serieId) {
        ListaFavoritos lista = repository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada com id: " + listaId));

        com.streamingflix.serraflixgrupo5.entity.Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new ResourceNotFoundException("Série não encontrada com id: " + serieId));

        lista.getSeries().add(serie);
        lista = repository.save(lista);
        return converterParaResponseDTO(lista);
    }

    public ListaFavoritosResponseDTO copiarLista(Long listaId, Long novoUsuarioId) {
        ListaFavoritos listaOriginal = repository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista original não encontrada com id: " + listaId));

        Usuario novoUsuario = usuarioRepository.findById(novoUsuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário de destino não encontrado com id: " + novoUsuarioId));

        ListaFavoritos novaLista = new ListaFavoritos();
        novaLista.setNomeLista("Cópia de - " + listaOriginal.getNomeLista());
        novaLista.setPrivada(true);
        novaLista.setDataCriacao(LocalDate.now());
        novaLista.setUsuario(novoUsuario);

        novaLista.setFilmes(new ArrayList<>(listaOriginal.getFilmes()));
        novaLista.setSeries(new ArrayList<>(listaOriginal.getSeries()));

        novaLista = repository.save(novaLista);
        return converterParaResponseDTO(novaLista);
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