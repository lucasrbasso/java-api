package com.gvendas.gestaovendas.services;

import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.exception.ServiceValidationException;
import com.gvendas.gestaovendas.repositories.CategoriaRepositorio;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoriaServico {
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    public List<Categoria> listarTodas(){
        return this.categoriaRepositorio.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id){
        return this.categoriaRepositorio.findById(id);
    }

    public Categoria salvar(Categoria categoria) {
        validarCategoriaDuplicada(categoria);
        return this.categoriaRepositorio.save(categoria);
    }

    public Categoria atualizar(Long codigo, Categoria categoria) {
        Categoria categoriaFromBD = validarCategoriaExiste(codigo);
        validarCategoriaDuplicada(categoria);
        BeanUtils.copyProperties(categoria, categoriaFromBD, "codigo" );
        return categoriaRepositorio.save(categoriaFromBD);
    }

    public void deletar(Long codigo) {
        validarCategoriaExiste(codigo);
        this.categoriaRepositorio.deleteById(codigo);
    }

    private Categoria validarCategoriaExiste(Long codigo) {
        Optional<Categoria> categoria = buscarPorId(codigo);

        if(categoria.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return categoria.get();
    }

    public void validarCategoriaDuplicada(Categoria categoria) {
        Categoria categoriaEncontrada = this.categoriaRepositorio.findByNome(categoria.getNome());

        if(categoriaEncontrada != null && !Objects.equals(categoriaEncontrada.getCodigo(), categoria.getCodigo())) {
            throw new ServiceValidationException(
                    String.format("A categoria %s já está cadastrada na categoria %d", categoria.getNome().toUpperCase(), categoriaEncontrada.getCodigo())
            );
        }
    }
}
