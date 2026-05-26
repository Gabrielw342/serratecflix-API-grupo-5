package com.streamingflix_avaliacao.service;

import com.streamingflix_avaliacao.dto.request.SerieRequestDTO;
import com.streamingflix_avaliacao.dto.response.SerieResponseDTO;
import com.streamingflix_avaliacao.entity.Categoria;
import com.streamingflix_avaliacao.entity.Serie;
import com.streamingflix_avaliacao.repository.CategoriaRepository;
import com.streamingflix_avaliacao.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<SerieResponseDTO> listar() {
        return serieRepository.findAll().stream()
                .map(SerieResponseDTO::new)
                .collect(Collectors.toList());
    }

    public SerieResponseDTO salvar(SerieRequestDTO dto) {
        Serie entity = new Serie();
        entity.setTitulo(dto.getTitulo());
        entity.setDescricao(dto.getDescricao());
        entity.setTemporadas(dto.getTemporadas());
        entity.setEpisodios(dto.getEpisodios());
        
        
        entity.setDataLancamento(dto.getDataLancamento());
        entity.setClassificacaoIndicativa(dto.getClassificacaoIndicativa());
       
        
        if (dto.getCategoriasIds() != null && !dto.getCategoriasIds().isEmpty()) {
            List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriasIds());
            entity.setCategorias(categorias);
        } else {
            entity.setCategorias(new ArrayList<>());
        }

        entity = serieRepository.save(entity);
        return new SerieResponseDTO(entity);
    }
}