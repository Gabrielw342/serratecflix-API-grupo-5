package com.streamingflix_avaliacao.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

public class FilmeRequestDTO {
    
    @NotBlank(message = "O título é obrigatório")
    private String titulo;
    
    private String descricao;
    
    @NotNull(message = "A duração é obrigatória")
    @Positive(message = "A duração deve ser maior que zero")
    private Integer duracao;

    private LocalDate dataLancamento;
    
  
    @NotNull(message = "A classificação indicativa é obrigatória")
    private Integer classificacaoIndicativa;
    
    @NotNull(message = "A lista de categorias é obrigatória")
    private List<Long> categoriasIds;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public Integer getDuracao() { return duracao; }
    public void setDuracao(Integer duracao) { this.duracao = duracao; }

    public LocalDate getDataLancamento() { return dataLancamento; }
    public void setDataLancamento(LocalDate dataLancamento) { this.dataLancamento = dataLancamento; }
    
 
    public Integer getClassificacaoIndicativa() { return classificacaoIndicativa; }
    public void setClassificacaoIndicativa(Integer classificacaoIndicativa) { this.classificacaoIndicativa = classificacaoIndicativa; }
    
    public List<Long> getCategoriasIds() { return categoriasIds; }
    public void setCategoriasIds(List<Long> categoriasIds) { this.categoriasIds = categoriasIds; }
}