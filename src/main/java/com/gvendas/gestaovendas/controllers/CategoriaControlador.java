package com.gvendas.gestaovendas.controllers;

import com.gvendas.gestaovendas.dto.categoria.CategoriaRequestDTO;
import com.gvendas.gestaovendas.dto.categoria.CategoriaResponseDTO;
import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.services.CategoriaServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags="Categoria")
@RestController
@RequestMapping("/category")
public class CategoriaControlador {
  @Autowired
  private CategoriaServico categoriaServico;

  @ApiOperation(value="Listar", nickname = "listarTodos")
  @GetMapping()
  public List<CategoriaResponseDTO> listarTodas() {
    return this.categoriaServico.listarTodas().stream()
      .map(CategoriaResponseDTO::converterParaCategoriaDTO)
      .collect(Collectors.toList());
  }

  @ApiOperation(value="Listar por código", nickname = "listarPorCodigo")
  @GetMapping("/{id}")
  public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
    Optional<Categoria> categoria = this.categoriaServico.buscarPorId(id);
    return categoria.map(value -> ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(value)))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @ApiOperation(value="Cadastrar", nickname = "cadastrarCategoria")
  @PostMapping()
  public ResponseEntity<CategoriaResponseDTO> salvar(@Valid @RequestBody CategoriaRequestDTO categoriaDto) {
    Categoria categoriaSalva = this.categoriaServico.salvar(categoriaDto.converterParaEntidade());
    return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaResponseDTO.converterParaCategoriaDTO(categoriaSalva));
  }

  @ApiOperation(value="Atualizar", nickname = "atualizar")
  @PutMapping("/{id}")
  public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoriaDto) {
    Categoria categoriaAtualizada = this.categoriaServico.atualizar(id, categoriaDto.converterParaEntidade(id));
    return ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(categoriaAtualizada));
  }

  @ApiOperation(value="Deletar", nickname = "deletarProduto")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    this.categoriaServico.deletar(id);
  }
}
