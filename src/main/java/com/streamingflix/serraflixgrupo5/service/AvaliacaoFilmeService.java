package com.streamingflix.serraflixgrupo5.service;

import com.streamingflix.serraflixgrupo5.dto.request.AvaliacaoFilmeRequest;
import com.streamingflix.serraflixgrupo5.dto.response.AvaliacaoFilmeResponse;
import com.streamingflix.serraflixgrupo5.dto.response.PaginacaoResponse;
import com.streamingflix.serraflixgrupo5.entity.AvaliacaoFilme;
import com.streamingflix.serraflixgrupo5.entity.Filme;
import com.streamingflix.serraflixgrupo5.entity.Usuario;
import com.streamingflix.serraflixgrupo5.repository.AvaliacaoFilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoFilmeService {

    @Autowired
    private AvaliacaoFilmeRepository avaliacaoFilmeRepository;

    public AvaliacaoFilmeResponse salvar(AvaliacaoFilmeRequest request) {

        AvaliacaoFilme avaliacao = new AvaliacaoFilme();
        avaliacao.setNota(request.getNota());
        avaliacao.setComentario(request.getComentario());

        Filme filme = new Filme();
        filme.setId(request.getFilmeId());
        avaliacao.setFilme(filme);

        Usuario usuario = new Usuario();
        usuario.setId(request.getUsuarioId());
        avaliacao.setUsuario(usuario);

        AvaliacaoFilme avaliacaoSalva = avaliacaoFilmeRepository.save(avaliacao);

        return new AvaliacaoFilmeResponse(
                avaliacaoSalva.getId(),
                avaliacaoSalva.getNota(),
                avaliacaoSalva.getComentario(),
                avaliacaoSalva.getFilme().getId(),
                avaliacaoSalva.getUsuario().getId());
    }

    public PaginacaoResponse<AvaliacaoFilmeResponse> listarPaginado(Pageable pageable) {
        Page<AvaliacaoFilme> page = avaliacaoFilmeRepository.findAll(pageable);

        List<AvaliacaoFilmeResponse> conteudo = page.getContent()
                .stream()
                .map(a -> new AvaliacaoFilmeResponse(
                        a.getId(),
                        a.getNota(),
                        a.getComentario(),
                        a.getFilme().getId(),
                        a.getUsuario().getId()))
                .collect(Collectors.toList());

        return new PaginacaoResponse<>(conteudo, page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }
}