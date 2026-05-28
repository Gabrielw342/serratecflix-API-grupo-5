package com.streamingflix.serraflixgrupo5.dto.response;

import java.util.List;

public class PaginacaoResponse<T> {

    private List<T> conteudo;
    private int paginaAtual;
    private int totalPaginas;
    private long totalElementos;

    public PaginacaoResponse() {
    }

    public PaginacaoResponse(List<T> conteudo, int paginaAtual, int totalPaginas, long totalElementos) {
        this.conteudo = conteudo;
        this.paginaAtual = paginaAtual;
        this.totalPaginas = totalPaginas;
        this.totalElementos = totalElementos;
    }

    public List<T> getConteudo() {
        return conteudo;
    }

    public void setConteudo(List<T> conteudo) {
        this.conteudo = conteudo;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(int paginaAtual) {
        this.paginaAtual = paginaAtual;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(long totalElementos) {
        this.totalElementos = totalElementos;
    }
}