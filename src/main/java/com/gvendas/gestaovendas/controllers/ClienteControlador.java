package com.gvendas.gestaovendas.controllers;

import com.gvendas.gestaovendas.dto.cliente.ClienteResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.services.ClienteServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags="Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteControlador {
  @Autowired
  private ClienteServico clienteServico;

  @ApiOperation(value="Listar", nickname = "listarTodosClientes")
  @GetMapping()
  public List<ClienteResponseDTO> listarTodos() {
    return this.clienteServico
      .listarTodos()
      .stream()
      .map(ClienteResponseDTO::converterParaClienteDTO)
      .collect(Collectors.toList());
  }

  @ApiOperation(value="Listar por código", nickname = "listarClientePorId")
  @GetMapping("/{id}")
  public ResponseEntity<ClienteResponseDTO> showById(@PathVariable Long id) {
    Optional<Cliente> clienteReturn = this.clienteServico.buscarPorCodigo(id);
    return clienteReturn.map(
      cliente -> ResponseEntity.ok(ClienteResponseDTO.converterParaClienteDTO(cliente)))
        .orElseGet(() -> ResponseEntity.notFound().build()
    );
  }

  @ApiOperation(value="Cadastrar", nickname = "salvarCliente")
  @PostMapping()
  public ResponseEntity<ClienteResponseDTO> salvar(@RequestBody Cliente cliente) {
    Cliente clienteReturn = this.clienteServico.salvar(cliente);
    return ResponseEntity.status(HttpStatus.CREATED).body(ClienteResponseDTO.converterParaClienteDTO(cliente));
  }
}