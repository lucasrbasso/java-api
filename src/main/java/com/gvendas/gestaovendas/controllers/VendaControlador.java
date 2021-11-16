package com.gvendas.gestaovendas.controllers;
import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.services.VendaServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags="Venda")
@RestController
@RequestMapping("/vendas")
public class VendaControlador {

    @Autowired
    private VendaServico vendaServico;

    @ApiOperation(value = "Listar vendas por cliente", nickname = "listarVendaPorCLiente")
    @GetMapping("/cliente/{codigoCliente}")
    public ResponseEntity<ClienteVendaResponseDTO> listarVendaPorCLiente(@PathVariable Long codigoCliente) {
        return ResponseEntity.ok(vendaServico.listarVendaPorCliente(codigoCliente));
    }

    @ApiOperation(value = "Buscar por venda", nickname = "buscarPorVenda")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteVendaResponseDTO> buscarPorVenda(@PathVariable Long id) {
        return ResponseEntity.ok(vendaServico.listarVendaPorCodigo(id));
    }

    @ApiOperation(value = "Registrar venda", nickname = "salvarVenda")
    @PostMapping("/cliente/{codigoCliente}")
    public ResponseEntity<ClienteVendaResponseDTO> salvar(@PathVariable Long codigoCliente, @Valid @RequestBody VendaRequestDTO venda) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaServico.salvar(codigoCliente, venda));
    }

    @ApiOperation(value = "Atualizar venda", nickname = "atualizarVenda")
    @PutMapping("/{id}/cliente/{codigoCliente}")
    public ResponseEntity<ClienteVendaResponseDTO> atualizar(@PathVariable Long id, @PathVariable Long codigoCliente, @Valid @RequestBody VendaRequestDTO venda) {
        return ResponseEntity.ok(vendaServico.atualizar(id, codigoCliente, venda));
    }

    @ApiOperation(value = "Deletar venda", nickname = "deletarVenda")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vendaServico.deletar(id);
    }
}
