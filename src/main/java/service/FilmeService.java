package service;

import java.util.ArrayList;
import java.util.List;

import entity.Categoria;
import repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.request.FilmeRequestDTO;
import dto.response.FilmeResponseDTO;
import entity.Filme;
import repository.FilmeRepository;

@Service
public class FilmeService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

    @Autowired
    private FilmeRepository filmeRepository;
   

    public FilmeResponseDTO salvar(FilmeRequestDTO dto) {

        Filme filme = new Filme();

        filme.setTitulo(dto.getTitulo());
        filme.setDescricao(dto.getDescricao());
        filme.setDuracao(dto.getDuracao());
        filme.setDataLancamento(dto.getDataLancamento());
        filme.setClassificacaoIndicativa(dto.getClassificacaoIndicativa());
        filme.setNotaMedia(dto.getNotaMedia());

        List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriasIds());

        filme.setCategorias(categorias);

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

        Filme filme = filmeRepository.findById(id).orElseThrow();

        return converterParaResponse(filme);
    }

    public void deletar(Long id) {

        filmeRepository.deleteById(id);
    }

    private FilmeResponseDTO converterParaResponse(Filme filme) {

        FilmeResponseDTO dto = new FilmeResponseDTO();

        dto.setId(filme.getId());
        dto.setTitulo(filme.getTitulo());
        dto.setDescricao(filme.getDescricao());
        dto.setDuracao(filme.getDuracao());
        dto.setDataLancamento(filme.getDataLancamento());
        dto.setClassificacaoIndicativa(filme.getClassificacaoIndicativa());
        dto.setNotaMedia(filme.getNotaMedia());

        List<String> categorias = new ArrayList<>();

        for (Categoria categoria : filme.getCategorias()) {
            categorias.add(categoria.getNome());
        }

        dto.setCategorias(categorias);

        return dto;
    }
}