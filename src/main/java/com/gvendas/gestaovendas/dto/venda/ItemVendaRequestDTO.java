package com.gvendas.gestaovendas.dto.venda;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel("Itens da venda requisição DTO")
public class ItemVendaRequestDTO {

    @NotNull(message="Código produto")
    @ApiModelProperty(value = "Código produto")
    private Long codigoProduto;

    @NotNull(message="Quantidade")
    @Min(value=1, message="Quantidade")
    @ApiModelProperty(value = "Quantidade")
    private Integer quantidade;

    @NotNull(message="Preço vendido")
    @ApiModelProperty(value = "Preço vendido")
    private BigDecimal precoVendido;

    public Long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoVendido() {
        return precoVendido;
    }

    public void setPrecoVendido(BigDecimal precoVendido) {
        this.precoVendido = precoVendido;
    }
}
