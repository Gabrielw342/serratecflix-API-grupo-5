package com.streamingflix_avaliacao.service;

import com.streamingflix_avaliacao.dto.request.ListaFavoritosRequestDTO;
import com.streamingflix_avaliacao.dto.response.ListaFavoritosResponseDTO;
import com.streamingflix_avaliacao.entity.Filme;
import com.streamingflix_avaliacao.entity.Serie;
import com.streamingflix_avaliacao.entity.ListaFavoritos;
import com.streamingflix_avaliacao.entity.Usuario;
import com.streamingflix_avaliacao.exception.ResourceNotFoundException;
import com.streamingflix_avaliacao.repository.FilmeRepository;
import com.streamingflix_avaliacao.repository.SerieRepository;
import com.streamingflix_avaliacao.repository.ListaFavoritosRepository;
import com.streamingflix_avaliacao.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListaFavoritosService {

    private final ListaFavoritosRepository favoritosRepository;
    private final UsuarioRepository usuarioRepository;
    private final FilmeRepository filmeRepository;
    private final SerieRepository serieRepository;

    public ListaFavoritosService(ListaFavoritosRepository favoritosRepository, 
                                 UsuarioRepository usuarioRepository, 
                                 FilmeRepository filmeRepository,
                                 SerieRepository serieRepository) {
        this.favoritosRepository = favoritosRepository;
        this.usuarioRepository = usuarioRepository;
        this.filmeRepository = filmeRepository;
        this.serieRepository = serieRepository;
    }

    public List<ListaFavoritosResponseDTO> listar() {
        return favoritosRepository.findAll().stream()
                .map(ListaFavoritosResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ListaFavoritosResponseDTO salvar(ListaFavoritosRequestDTO dto) {
       
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. ID: " + dto.getUsuarioId()));

        ListaFavoritos entity = new ListaFavoritos();
        
        entity.setNomeLista(dto.getNomeLista());
        entity.setPrivada(dto.getPrivada());
        entity.setUsuario(usuario);

       
        if (dto.getFilmesIds() != null && !dto.getFilmesIds().isEmpty()) {
            List<Filme> filmes = filmeRepository.findAllById(dto.getFilmesIds());
            entity.setFilmes(filmes);
        } else {
            entity.setFilmes(new ArrayList<>());
        }

       
        if (dto.getSeriesIds() != null && !dto.getSeriesIds().isEmpty()) {
            List<Serie> series = serieRepository.findAllById(dto.getSeriesIds());
            entity.setSeries(series);
        } else {
            entity.setSeries(new ArrayList<>());
        }

        entity = favoritosRepository.save(entity);
        return new ListaFavoritosResponseDTO(entity);
    }
}