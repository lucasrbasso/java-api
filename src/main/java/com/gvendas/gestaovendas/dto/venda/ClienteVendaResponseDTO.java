package com.gvendas.gestaovendas.dto.venda;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("Cliente da venda retorno DTO")
public class ClienteVendaResponseDTO {
    @ApiModelProperty(value="Nome")
    private String nome;

    @ApiModelProperty(value="Vendas")
    private List<VendaResponseDTO> vendas;

    public ClienteVendaResponseDTO(String nome, List<VendaResponseDTO> vendas) {
        this.nome = nome;
        this.vendas = vendas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<VendaResponseDTO> getVendaResponseDTOs() {
        return vendas;
    }

    public void setVendaResponseDTOs(List<VendaResponseDTO> vendas) {
        this.vendas = vendas;
    }
}
