package com.gvendas.gestaovendas.repositories;

import com.gvendas.gestaovendas.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
  Optional<Cliente> findByNome(String nome);
}
