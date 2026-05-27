package com.streamingflix.serraflixgrupo5.service;

import com.streamingflix.serraflixgrupo5.dto.request.AvaliacaoSerieRequest;
import com.streamingflix.serraflixgrupo5.dto.response.AvaliacaoSerieResponse;
import com.streamingflix.serraflixgrupo5.entity.AvaliacaoSerie;
import com.streamingflix.serraflixgrupo5.entity.Serie;
import com.streamingflix.serraflixgrupo5.entity.Usuario;
import com.streamingflix.serraflixgrupo5.repository.AvaliacaoSerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoSerieService {

    @Autowired
    private AvaliacaoSerieRepository avaliacaoSerieRepository;

    public AvaliacaoSerieResponse salvar(AvaliacaoSerieRequest request) {
        
        AvaliacaoSerie avaliacao = new AvaliacaoSerie();
        avaliacao.setNota(request.getNota());
        avaliacao.setComentario(request.getComentario());

        Serie serie = new Serie();
        serie.setId(request.getSerieId());
        avaliacao.setSerie(serie);

        Usuario usuario = new Usuario();
        usuario.setId(request.getUsuarioId());
        avaliacao.setUsuario(usuario);

        AvaliacaoSerie avaliacaoSalva = avaliacaoSerieRepository.save(avaliacao);

        return new AvaliacaoSerieResponse(
                avaliacaoSalva.getId(),
                avaliacaoSalva.getNota(),
                avaliacaoSalva.getComentario(),
                avaliacaoSalva.getSerie().getId(),
                avaliacaoSalva.getUsuario().getId()
        );
    }
}