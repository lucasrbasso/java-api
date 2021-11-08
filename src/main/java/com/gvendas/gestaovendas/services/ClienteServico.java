package com.gvendas.gestaovendas.services;

import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.exception.ServiceValidationException;
import com.gvendas.gestaovendas.repositories.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteServico {

  @Autowired
  private ClienteRepositorio clienteRepositorio;

  public List<Cliente> listarTodos() {
    return this.clienteRepositorio.findAll();
  }

  public Optional<Cliente> buscarPorCodigo(Long codigo) {
    return this.clienteRepositorio.findById(codigo);
  }

  public Cliente salvar(Cliente cliente) {
    validaNomeJaExiste(cliente);
    return this.clienteRepositorio.save(cliente);
  }

  private void validaNomeJaExiste(Cliente cliente) {
    Optional<Cliente> clienteFromBD = this.clienteRepositorio.findByNome(cliente.getNome());
    if (clienteFromBD.isPresent() && !Objects.equals(clienteFromBD.get().getCodigo(), cliente.getCodigo())) {
      throw  new ServiceValidationException(
        String.format("O cliente %s já está cadastrado", cliente.getNome().toUpperCase()));
    }
  }
}

