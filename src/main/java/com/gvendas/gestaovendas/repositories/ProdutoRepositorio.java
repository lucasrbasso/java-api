package com.gvendas.gestaovendas.repositories;

import com.gvendas.gestaovendas.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
  List<Produto> findByCategoriaCodigo(Long codigoCategoria);

  Optional<Produto> findByCategoriaCodigoAndDescricao(Long codigoCategoria, String descricao);
}

