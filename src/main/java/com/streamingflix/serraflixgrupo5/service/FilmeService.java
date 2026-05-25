package com.streamingflix.serraflixgrupo5.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.streamingflix.serraflixgrupo5.dto.request.FilmeRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.FilmeResponseDTO;
import com.streamingflix.serraflixgrupo5.entity.Categoria;
import com.streamingflix.serraflixgrupo5.entity.Filme;
import com.streamingflix.serraflixgrupo5.exception.BadRequestException;
import com.streamingflix.serraflixgrupo5.exception.ResourceNotFoundException;
import com.streamingflix.serraflixgrupo5.repository.CategoriaRepository;
import com.streamingflix.serraflixgrupo5.repository.FilmeRepository;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public FilmeResponseDTO salvar(FilmeRequestDTO dto) {

        if (dto.getDuracao() <= 0) {
            throw new BadRequestException(
                    "ERRO! duração deve ser maior que zero");
        }

        if (dto.getNotaMedia() != null &&
                (dto.getNotaMedia() < 0 || dto.getNotaMedia() > 10)) {

            throw new BadRequestException(
                    "ERRO! nota deve estar entre 0 e 10");
        }

        Filme filme = new Filme();

        filme.setTitulo(dto.getTitulo());
        filme.setDescricao(dto.getDescricao());
        filme.setDuracao(dto.getDuracao());
        filme.setDataLancamento(dto.getDataLancamento());
        filme.setClassificacaoIndicativa(dto.getClassificacaoIndicativa());
        filme.setNotaMedia(dto.getNotaMedia());

        
        // relacionamento many to many com categorias que quando o matheus terminar
        //eu ja deixei preparado para funcionar quando Categoria estiver pronta
        
        if (dto.getCategoriasIds() != null) {

            List<Categoria> categorias =
                    categoriaRepository.findAllById(dto.getCategoriasIds());

            filme.setCategorias(categorias);
        }

        Filme filmeSalvo = filmeRepository.save(filme);

        return converterParaResponse(filmeSalvo);
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
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ERRO! Filme não encontrado"));

        return converterParaResponse(filme);
    }

    public FilmeResponseDTO atualizar(Long id, FilmeRequestDTO dto) {

        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ERRO! Filme não encontrado"));

        
        if (dto.getDuracao() <= 0) {
            throw new BadRequestException(
                    "ERRO! duração deve ser maior que zero");
        }

        if (dto.getNotaMedia() != null &&
                (dto.getNotaMedia() < 0 || dto.getNotaMedia() > 10)) {

            throw new BadRequestException(
                    "ERRO! nota deve estar entre 0 e 10");
        }

        filme.setTitulo(dto.getTitulo());
        filme.setDescricao(dto.getDescricao());
        filme.setDuracao(dto.getDuracao());
        filme.setDataLancamento(dto.getDataLancamento());
        filme.setClassificacaoIndicativa(dto.getClassificacaoIndicativa());
        filme.setNotaMedia(dto.getNotaMedia());

        if (dto.getCategoriasIds() != null) {

            List<Categoria> categorias =
                    categoriaRepository.findAllById(dto.getCategoriasIds());

            filme.setCategorias(categorias);
        }

        Filme filmeAtualizado = filmeRepository.save(filme);

        return converterParaResponse(filmeAtualizado);
    }
    
    public void deletar(Long id) {

        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ERRO! Filme não encontrado"));

        filmeRepository.delete(filme);
    }
    
    private FilmeResponseDTO converterParaResponse(Filme filme) {

        FilmeResponseDTO dto = new FilmeResponseDTO();

        dto.setId(filme.getId());
        dto.setTitulo(filme.getTitulo());
        dto.setDescricao(filme.getDescricao());
        dto.setDuracao(filme.getDuracao());
        dto.setDataLancamento(filme.getDataLancamento());
        dto.setClassificacaoIndicativa(
                filme.getClassificacaoIndicativa());
        dto.setNotaMedia(filme.getNotaMedia());


        
        List<String> categorias = new ArrayList<>();

       
        // deixei null para poder fazer testes antes da entidade Categoria ficar pronta e eu puder fazer o pull
        
        if (filme.getCategorias() != null) {

            for (Categoria categoria : filme.getCategorias()) {

                categorias.add(categoria.getNome());
            }
        }

        dto.setCategorias(categorias);

        return dto;
    }
    
    public List<FilmeResponseDTO> listarPorMaiorNota() {

        List<Filme> filmes =
                filmeRepository.findAllByOrderByNotaMediaDesc();

        List<FilmeResponseDTO> resposta = new ArrayList<>();

        for (Filme filme : filmes) {

            resposta.add(converterParaResponse(filme));
        }

        return resposta;
    }
}