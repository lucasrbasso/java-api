package com.gvendas.gestaovendas.controllers;

import com.gvendas.gestaovendas.dto.produto.ProdutoRequestDTO;
import com.gvendas.gestaovendas.dto.produto.ProdutoResponseDTO;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.services.ProdutoServico;
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

@Api(tags="Produto")
@RestController
@RequestMapping("/product")
public class ProdutoControlador {
  @Autowired
  private ProdutoServico produtoServico;

  @ApiOperation(value="Listar", nickname = "listarTodos")
  @GetMapping()
  public List<ProdutoResponseDTO> listarTodas() {
        return this.produtoServico.listarTodos()
          .stream()
          .map(ProdutoResponseDTO::converterParaProdutoDTO)
          .collect(Collectors.toList());
    }

    @ApiOperation(value="Listar por id", nickname = "listarPorId")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> showById(@PathVariable Long id) {
        Optional<Produto> produto = this.produtoServico.buscarPorId(id);
        return produto.map(value ->
          ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(value)))
          .orElseGet(() -> ResponseEntity.notFound().build()
        );
    }

    @ApiOperation(value="Listar por Categoria", nickname = "listarPorCategoria")
    @GetMapping("/category/{id}")
    public List<ProdutoResponseDTO> indexByCategoryId(@PathVariable Long id) {
        return this.produtoServico.listarPorCategoria(id)
          .stream()
          .map(ProdutoResponseDTO::converterParaProdutoDTO)
          .collect(Collectors.toList());
    }

    @ApiOperation(value="Salvar", nickname = "salvarProduto")
    @PostMapping()
    public ResponseEntity<ProdutoResponseDTO> salvar(@Valid @RequestBody ProdutoRequestDTO produto) {
        Produto produtoSalvo = this.produtoServico.salvar(produto.converterParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoResponseDTO.converterParaProdutoDTO(produtoSalvo));
    }

    @ApiOperation(value="Atualizar", nickname = "atualizarProduto")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> update(@Valid @RequestBody ProdutoRequestDTO produto , @PathVariable Long id) {
        Produto produtoAtualizado = this.produtoServico.atualizar(
          produto.converterParaEntidade(id), id, produto.getCodigoCategoria()
        );
        return ResponseEntity.ok().body(ProdutoResponseDTO.converterParaProdutoDTO(produtoAtualizado));
    }

    @ApiOperation(value="Deletar", nickname = "deletarProduto")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.produtoServico.deletar(id);
    }
}
