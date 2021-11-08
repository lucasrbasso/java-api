package com.gvendas.gestaovendas.dto.produto;

import com.gvendas.gestaovendas.dto.categoria.CategoriaRequestDTO;
import com.gvendas.gestaovendas.dto.categoria.CategoriaResponseDTO;
import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.entidades.Produto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel("Produto requisição DTO")
public class ProdutoRequestDTO {

  @NotBlank(message="Descrição")
  @Length(min = 3, max = 100, message="Descrição")
  @ApiModelProperty(value = "Descrição")
  private String descricao;

  @NotNull(message="Quantidade")
  @ApiModelProperty(value = "Quantidade")
  private Integer quantidade;

  @NotNull(message="Preço Custo")
  @ApiModelProperty(value = "Preço custo")
  private BigDecimal precoCusto;

  @NotNull(message="Preço Venda")
  @ApiModelProperty(value = "Preço venda")
  private BigDecimal precoVenda;

  @Length(max = 500, message="Observação")
  @ApiModelProperty(value = "Observação")
  private String observacao;

  @NotNull(message="Código categoria")
  @ApiModelProperty(value = "CodigoCategoria")
  private Long codigoCategoria;

  public  Produto converterParaEntidade() {
    return new Produto(descricao, quantidade, precoCusto, precoVenda, observacao, new Categoria(codigoCategoria));
  }

  public  Produto converterParaEntidade(Long codigoProduto) {
    return new Produto(codigoProduto, descricao, quantidade, precoCusto, precoVenda, observacao, new Categoria(codigoCategoria));
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }

  public BigDecimal getPrecoCusto() {
    return precoCusto;
  }

  public void setPrecoCusto(BigDecimal precoCusto) {
    this.precoCusto = precoCusto;
  }

  public BigDecimal getPrecoVenda() {
    return precoVenda;
  }

  public void setPrecoVenda(BigDecimal precoVenda) {
    this.precoVenda = precoVenda;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public Long getCodigoCategoria() {
    return codigoCategoria;
  }

  public void setCodigoCategoria(Long codigoCategoria) {
    this.codigoCategoria = codigoCategoria;
  }
}
