package com.streamingflix_avaliacao.service;

import com.streamingflix_avaliacao.dto.request.FilmeRequestDTO;
import com.streamingflix_avaliacao.dto.response.FilmeResponseDTO;
import com.streamingflix_avaliacao.entity.Categoria;
import com.streamingflix_avaliacao.entity.Filme;
import com.streamingflix_avaliacao.repository.CategoriaRepository;
import com.streamingflix_avaliacao.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final CategoriaRepository categoriaRepository;

    public FilmeService(FilmeRepository filmeRepository, CategoriaRepository categoriaRepository) {
        this.filmeRepository = filmeRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<FilmeResponseDTO> listar() {
        return filmeRepository.findAll().stream()
                .map(FilmeResponseDTO::new)
                .collect(Collectors.toList());
    }

    public FilmeResponseDTO salvar(FilmeRequestDTO dto) {
        Filme entity = new Filme();
        entity.setTitulo(dto.getTitulo());
        entity.setDescricao(dto.getDescricao());
        entity.setDuracao(dto.getDuracao());
        
        if (dto.getDataLancamento() != null) {
            entity.setDataLancamento(dto.getDataLancamento());
        }
        
        entity.setClassificacaoIndicativa(dto.getClassificacaoIndicativa());
        
        if (dto.getCategoriasIds() != null && !dto.getCategoriasIds().isEmpty()) {
            List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriasIds());
            entity.setCategorias(categorias);
        } else {
            entity.setCategorias(new ArrayList<>());
        }

        entity = filmeRepository.save(entity);
        return new FilmeResponseDTO(entity);
    }
}