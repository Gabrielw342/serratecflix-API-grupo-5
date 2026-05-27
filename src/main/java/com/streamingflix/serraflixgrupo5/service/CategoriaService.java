package com.streamingflix.serraflixgrupo5.service;

import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.streamingflix.serraflixgrupo5.dto.request.CategoriaDTORequest;
import com.streamingflix.serraflixgrupo5.dto.response.CategoriaDTOResponse;
import com.streamingflix.serraflixgrupo5.entity.Categoria;
import com.streamingflix.serraflixgrupo5.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaDTOResponse inserir(CategoriaDTORequest categoriaDto) {
    
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDto.getNome());
        categoria.setDescricao(categoriaDto.getDescricao());

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return new CategoriaDTOResponse(categoriaSalva);
    }

    public List<CategoriaDTOResponse> listar() {
        List<Categoria> categorias = categoriaRepository.findAll();
        
        return categorias.stream()
                .map(CategoriaDTOResponse::new)
                .toList();
    }

    public CategoriaDTOResponse buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(CategoriaDTOResponse::new)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public CategoriaDTOResponse atualizar(Long id, CategoriaDTORequest categoriaDto) {

        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setDescricao(categoriaDto.getDescricao());
                    categoria.setNome(categoriaDto.getNome());
                
                    return new CategoriaDTOResponse(categoriaRepository.save(categoria));
                })
                .orElse(null);
    }

    public boolean deletar(Long id) {

        if (!categoriaRepository.existsById(id)) {
            return false;
        }

        categoriaRepository.deleteById(id);
        return true;
    }
}