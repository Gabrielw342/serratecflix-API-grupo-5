package com.streamingflix_avaliacao.service;

import com.streamingflix_avaliacao.dto.request.CategoriaRequestDTO;
import com.streamingflix_avaliacao.dto.response.CategoriaResponseDTO;
import com.streamingflix_avaliacao.entity.Categoria;
import com.streamingflix_avaliacao.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaResponseDTO> listar() {
        return repository.findAll().stream()
                .map(CategoriaResponseDTO::new)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        Categoria entity = new Categoria();
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());

        entity = repository.save(entity);
        return new CategoriaResponseDTO(entity);
    }
}