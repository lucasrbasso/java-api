package com.gvendas.gestaovendas.services;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.entidades.Venda;
import com.gvendas.gestaovendas.exception.ServiceValidationException;
import com.gvendas.gestaovendas.repositories.ItemVendaRepositorio;
import com.gvendas.gestaovendas.repositories.VendaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaServico extends AbstractVendaServico {
    private VendaRepositorio vendaRepositorio;
    private ItemVendaRepositorio itensVendaRepositorio;
    private ClienteServico clienteServico;
    private ProdutoServico produtoServico;

    @Autowired
    public VendaServico(VendaRepositorio vendaRepositorio, ClienteServico clienteServico, ItemVendaRepositorio itensVendaRepositorio, ProdutoServico produtoServico) {
        this.vendaRepositorio = vendaRepositorio;
        this.clienteServico = clienteServico;
        this.itensVendaRepositorio = itensVendaRepositorio;
        this.produtoServico = produtoServico;
    }

    public ClienteVendaResponseDTO listarVendaPorCliente(Long codigoCliente) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<VendaResponseDTO> vendaResponseDTOList = vendaRepositorio
                .findByClienteCodigo(codigoCliente)
                .stream()
                .map(venda -> criandoVendaResponseDTO(venda, itensVendaRepositorio.findByVendaPorCodigo(venda.getCodigo())))
                .collect(Collectors.toList());
        return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDTOList);
    }
    
    public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
        Venda venda = validarVendaExiste(codigoVenda);
        List<ItemVenda> itens = itensVendaRepositorio.findByVendaPorCodigo(venda.getCodigo());
        return createClienteVendaResponseDTO(venda, itens);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO venda) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        validarProdutoExisteEAtualizarQuantidade(venda.getItens());
        Venda vendaSalva = salvarVenda(cliente, venda);
        List<ItemVenda> itens = itensVendaRepositorio.findByVendaPorCodigo(vendaSalva.getCodigo());
        return createClienteVendaResponseDTO(vendaSalva, itens);
    }

    public ClienteVendaResponseDTO atualizar(Long codigoVenda, Long codigoCliente, VendaRequestDTO vendaDto) {
        validarVendaExiste(codigoVenda);
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<ItemVenda> itensVendaList = itensVendaRepositorio.findByVendaPorCodigo(codigoVenda);
        validarProdutoExisteEDevolverEstoque(itensVendaList);
        validarProdutoExisteEAtualizarQuantidade(vendaDto.getItens());
        itensVendaRepositorio.deleteAll(itensVendaList);
        Venda vendaAtualizada = atualizarVenda(codigoVenda, cliente, vendaDto);
        List<ItemVenda> itens = itensVendaRepositorio.findByVendaPorCodigo(vendaAtualizada.getCodigo());
        return createClienteVendaResponseDTO(vendaAtualizada,itens);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void deletar(Long codigoVenda) {
        validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVenda = itensVendaRepositorio.findByVendaPorCodigo(codigoVenda);
        validarProdutoExisteEDevolverEstoque(itensVenda);
        itensVendaRepositorio.deleteAll(itensVenda);
        vendaRepositorio.deleteById(codigoVenda);
    }

    private void validarProdutoExisteEDevolverEstoque(List<ItemVenda> itemVendas) {
        itemVendas.forEach(item -> {
            Produto produto = produtoServico.validarProdutoExiste(item.getProduto().getCodigo());
            produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
            produtoServico.atualizarQuantidadeEmEstoque(produto);
        });
    }

    private Venda salvarVenda(Cliente cliente, VendaRequestDTO venda) {
        Venda vendaSalva = vendaRepositorio.save(new Venda(venda.getData(), cliente));
        venda.getItens()
                .stream()
                .map(item -> criandoItemVenda(item, vendaSalva))
                .forEach(itensVendaRepositorio::save);
        return vendaSalva;
    }

    private Venda atualizarVenda(Long codigoVenda, Cliente cliente, VendaRequestDTO venda) {
        Venda vendaSalva = vendaRepositorio.save(new Venda(codigoVenda, venda.getData(), cliente));
        venda.getItens()
                .stream()
                .map(item -> criandoItemVenda(item, vendaSalva))
                .forEach(itensVendaRepositorio::save);
        return vendaSalva;
    }

    private void validarProdutoExisteEAtualizarQuantidade(List<ItemVendaRequestDTO> itens) {
        itens.forEach(item -> {
            Produto produto = produtoServico.validarProdutoExiste(item.getCodigoProduto());
            validarQuantidadeProdutoExiste(produto, item.getQuantidade());
            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            produtoServico.atualizarQuantidadeEmEstoque(produto);
        });
    }

    private void validarQuantidadeProdutoExiste(Produto produto, Integer qtdeVendaDto) {
        if(produto.getQuantidade() < qtdeVendaDto) {
            throw new ServiceValidationException(
                    String.format("A quantidade %s informada para o produto %s não está disponível em estoque"
                    ,qtdeVendaDto,produto.getDescricao()));
        }
    }

    private Venda validarVendaExiste(Long codigoVenda) {
        Optional<Venda> venda = vendaRepositorio.findById(codigoVenda);
        if(venda.isEmpty()) {
            throw new ServiceValidationException(String.format("A venda de id: %s não existe no cadastro", codigoVenda));
        }
        return venda.get();
    }

    private Cliente validarClienteVendaExiste(Long codigoCliente) {
        Optional<Cliente> cliente = clienteServico.buscarPorCodigo(codigoCliente);
        if(cliente.isEmpty()) {
            throw new ServiceValidationException(String.format("O Cliente de id: %s não existe no cadastro", codigoCliente));
        }

        return cliente.get();
    }
}
