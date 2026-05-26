package com.streamingflix_avaliacao.dto.response;

import com.streamingflix_avaliacao.entity.Filme;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FilmeResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Integer duracao;
    private LocalDate dataLancamento;
    
   
    private Integer classificacaoIndicativa;
    
    private Double notaMedia;
    private List<String> categorias;

    public FilmeResponseDTO() {}

    public FilmeResponseDTO(Filme entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        this.descricao = entity.getDescricao();
        this.duracao = entity.getDuracao();
        this.dataLancamento = entity.getDataLancamento();
        this.classificacaoIndicativa = entity.getClassificacaoIndicativa();
        this.notaMedia = entity.getNotaMedia();
        
        if(entity.getCategorias() != null) {
            this.categorias = entity.getCategorias().stream()
                .map(c -> c.getNome())
                .collect(Collectors.toList());
        }
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public Integer getDuracao() { return duracao; }
    public LocalDate getDataLancamento() { return dataLancamento; }
    
    
    public Integer getClassificacaoIndicativa() { return classificacaoIndicativa; }
    
    public Double getNotaMedia() { return notaMedia; }
    public List<String> getCategorias() { return categorias; }
}