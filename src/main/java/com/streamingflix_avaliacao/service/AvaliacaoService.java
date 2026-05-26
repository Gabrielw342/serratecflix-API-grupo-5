package com.streamingflix_avaliacao.service;

import com.streamingflix_avaliacao.dto.request.AvaliacaoRequestDTO;
import com.streamingflix_avaliacao.dto.response.AvaliacaoResponseDTO;
import com.streamingflix_avaliacao.entity.AvaliacaoFilme;
import com.streamingflix_avaliacao.entity.AvaliacaoSerie;
import com.streamingflix_avaliacao.entity.Filme;
import com.streamingflix_avaliacao.entity.Serie;
import com.streamingflix_avaliacao.entity.Usuario;
import com.streamingflix_avaliacao.exception.ResourceNotFoundException;
import com.streamingflix_avaliacao.repository.AvaliacaoFilmeRepository;
import com.streamingflix_avaliacao.repository.AvaliacaoSerieRepository;
import com.streamingflix_avaliacao.repository.FilmeRepository;
import com.streamingflix_avaliacao.repository.SerieRepository;
import com.streamingflix_avaliacao.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoFilmeRepository avaliacaoFilmeRepository;
    private final AvaliacaoSerieRepository avaliacaoSerieRepository;
    private final UsuarioRepository usuarioRepository;
    private final FilmeRepository filmeRepository;
    private final SerieRepository serieRepository;

    public AvaliacaoService(AvaliacaoFilmeRepository avaliacaoFilmeRepository, 
                            AvaliacaoSerieRepository avaliacaoSerieRepository,
                            UsuarioRepository usuarioRepository, 
                            FilmeRepository filmeRepository, 
                            SerieRepository serieRepository) {
        this.avaliacaoFilmeRepository = avaliacaoFilmeRepository;
        this.avaliacaoSerieRepository = avaliacaoSerieRepository;
        this.usuarioRepository = usuarioRepository;
        this.filmeRepository = filmeRepository;
        this.serieRepository = serieRepository;
    }

    @Transactional
    public AvaliacaoResponseDTO avaliarFilme(AvaliacaoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        
        Filme filme = filmeRepository.findById(dto.getConteudoId())
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado."));

        AvaliacaoFilme avaliacao = new AvaliacaoFilme();
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setUsuario(usuario);
        avaliacao.setFilme(filme);

        avaliacao = avaliacaoFilmeRepository.save(avaliacao);

        List<AvaliacaoFilme> avaliacoes = avaliacaoFilmeRepository.findByFilmeId(filme.getId());
        
        double media = avaliacoes.stream()
                .mapToDouble(AvaliacaoFilme::getNota)
                .average()
                .orElse(0.0);
        
        filme.setNotaMedia(Math.round(media * 10.0) / 10.0);
        filmeRepository.save(filme);

        return new AvaliacaoResponseDTO(avaliacao);
    }

    @Transactional
    public AvaliacaoResponseDTO avaliarSerie(AvaliacaoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        
        Serie serie = serieRepository.findById(dto.getConteudoId())
                .orElseThrow(() -> new ResourceNotFoundException("Série não encontrada."));

        AvaliacaoSerie avaliacao = new AvaliacaoSerie();
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setUsuario(usuario);
        avaliacao.setSerie(serie);

        avaliacao = avaliacaoSerieRepository.save(avaliacao);

        List<AvaliacaoSerie> avaliacoes = avaliacaoSerieRepository.findBySerieId(serie.getId());
        
        double media = avaliacoes.stream()
                .mapToDouble(AvaliacaoSerie::getNota)
                .average()
                .orElse(0.0);
        
        serie.setNotaMedia(Math.round(media * 10.0) / 10.0);
        serieRepository.save(serie);

        return new AvaliacaoResponseDTO(avaliacao);
    }
}