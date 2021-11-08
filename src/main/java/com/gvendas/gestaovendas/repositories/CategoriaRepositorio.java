package com.gvendas.gestaovendas.repositories;

import com.gvendas.gestaovendas.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
  Categoria findByNome(String nome);
}
