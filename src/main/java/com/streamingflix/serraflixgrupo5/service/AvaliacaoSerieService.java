package com.streamingflix.serraflixgrupo5.service;

import com.streamingflix.serraflixgrupo5.dto.request.AvaliacaoSerieRequest;
import com.streamingflix.serraflixgrupo5.dto.response.AvaliacaoSerieResponse;
import com.streamingflix.serraflixgrupo5.entity.AvaliacaoSerie;
import com.streamingflix.serraflixgrupo5.entity.Serie;
import com.streamingflix.serraflixgrupo5.entity.Usuario;
import com.streamingflix.serraflixgrupo5.exception.ResourceNotFoundException;
import com.streamingflix.serraflixgrupo5.repository.AvaliacaoSerieRepository;
import com.streamingflix.serraflixgrupo5.repository.SerieRepository;
import com.streamingflix.serraflixgrupo5.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoSerieService {

    @Autowired
    private AvaliacaoSerieRepository avaliacaoSerieRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public List<AvaliacaoSerieResponse> listarPorSerie(Long serieId) {
        if (!serieRepository.existsById(serieId)) {
            throw new ResourceNotFoundException("Série não encontrada com id: " + serieId);
        }

        return avaliacaoSerieRepository.findBySerieId(serieId)
                .stream()
                .map(a -> new AvaliacaoSerieResponse(
                        a.getId(),
                        a.getNota(),
                        a.getComentario(),
                        a.getSerie().getId(),
                        a.getUsuario().getId()
                ))
                .collect(Collectors.toList());
    }

    public AvaliacaoSerieResponse avaliar(Long usuarioId, AvaliacaoSerieRequest request) {
        Serie serie = serieRepository.findById(request.getSerieId())
                .orElseThrow(() -> new ResourceNotFoundException("Série não encontrada com id: " + request.getSerieId()));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + usuarioId));

        AvaliacaoSerie avaliacao = new AvaliacaoSerie();
        avaliacao.setNota(request.getNota());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setSerie(serie);
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

    public void deletar(Long avaliacaoId) {
        if (!avaliacaoSerieRepository.existsById(avaliacaoId)) {
            throw new ResourceNotFoundException("Avaliação não encontrada com id: " + avaliacaoId);
        }
        avaliacaoSerieRepository.deleteById(avaliacaoId);
    }
}