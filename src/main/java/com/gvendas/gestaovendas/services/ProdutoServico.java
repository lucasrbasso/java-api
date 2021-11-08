package com.gvendas.gestaovendas.services;

import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.exception.ServiceValidationException;
import com.gvendas.gestaovendas.repositories.CategoriaRepositorio;
import com.gvendas.gestaovendas.repositories.ProdutoRepositorio;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    public List<Produto> listarTodos(){
        return this.produtoRepositorio.findAll();
    }

    public Optional<Produto> buscarPorId(Long id){
        return this.produtoRepositorio.findById(id);
    }

    public List<Produto> listarPorCategoria(Long idCategoria) {
        validarCategoriaDoProdutoExiste(idCategoria);
        return this.produtoRepositorio.findByCategoriaCodigo(idCategoria);
    }

    public Produto atualizar(Produto produto, Long codigoProduto, Long codigoCategoria) {
        Produto produtoSalvar = validarProdutoExiste(codigoProduto);
        validarCategoriaDoProdutoExiste(codigoCategoria);
        validarProdutoDuplicado(produto, codigoProduto);
        BeanUtils.copyProperties(produto, produtoSalvar, "codigo");
        return produtoRepositorio.save(produtoSalvar);
    }

    public Produto salvar(Produto produto) {
        validarCategoriaDoProdutoExiste(produto.getCategoria().getCodigo());
        validarProdutoDuplicado(produto, produto.getCodigo());
        return this.produtoRepositorio.save(produto);
    }

    public void deletar(Long codigoProduto) {
        validarProdutoExiste(codigoProduto);
        this.produtoRepositorio.deleteById(codigoProduto);
    }

    private void validarCategoriaDoProdutoExiste(Long codigoCategoria) {
        if (codigoCategoria == null) {
            throw new ServiceValidationException("A categoria não pode ser nula");
        }

        Optional<Categoria> categoria = this.categoriaRepositorio.findById(codigoCategoria);

        if(categoria.isEmpty()) {
            throw new ServiceValidationException(String.format("A categoria de código %s não existe no cadastro", codigoCategoria));
        }
    }

    private Produto validarProdutoExiste(Long codigoProduto) {
        Optional<Produto> produtoEncontrado = this.produtoRepositorio.findById(codigoProduto);

        if(produtoEncontrado.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return produtoEncontrado.get();
    }

    private void validarProdutoDuplicado (Produto produto, Long codigoProduto) {
        Optional<Produto> produtoEncontrado = this.produtoRepositorio
                .findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao());

        if(produtoEncontrado.isPresent() && !Objects.equals(produtoEncontrado.get().getCodigo(), codigoProduto)) {
            System.out.println(produtoEncontrado.get().getCodigo());
            System.out.println(produto.getCodigo());
            throw new ServiceValidationException(
                    String.format(
                            "O produto %s já está cadastrado na categoria %s",
                            produto.getDescricao(),
                            produtoEncontrado.get().getCategoria().getNome()
                    )
            );
        }

    }
}
