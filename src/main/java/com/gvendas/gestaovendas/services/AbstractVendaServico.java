package com.gvendas.gestaovendas.services;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.entidades.Venda;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractVendaServico {

    protected ItemVenda criandoItemVenda(ItemVendaRequestDTO itemVendaDTO, Venda venda) {
        return new ItemVenda(itemVendaDTO.getQuantidade(), itemVendaDTO.getPrecoVendido(), new Produto(itemVendaDTO.getCodigoProduto()), venda);
    }

    protected ClienteVendaResponseDTO createClienteVendaResponseDTO(Venda venda, List<ItemVenda> itens) {
        VendaResponseDTO vendaDto = criandoVendaResponseDTO(venda, itens);
        return new ClienteVendaResponseDTO(venda.getCliente().getNome(), List.of(vendaDto));
    }

    protected VendaResponseDTO criandoVendaResponseDTO(Venda venda, List<ItemVenda> itensVendaList) {
        List<ItemVendaResponseDTO> itensVendaResponseDTO = itensVendaList
                .stream()
                .map(this::criandoItensVendaReponseDTO)
                .collect(Collectors.toList());
        return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVendaResponseDTO);
    }

    protected ItemVendaResponseDTO criandoItensVendaReponseDTO(ItemVenda itemVenda) {
        return new ItemVendaResponseDTO(
                itemVenda.getCodigo(),
                itemVenda.getQuantidade(),
                itemVenda.getPrecoVendido(),
                itemVenda.getProduto().getCodigo(),
                itemVenda.getProduto().getDescricao()
        );
    }
}
