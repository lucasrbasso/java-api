package com.gvendas.gestaovendas.dto.cliente;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Cliente requisição DTO")
public class ClienteRequestDTO {

  @ApiModelProperty(value="Nome")
  private String nome;

  @ApiModelProperty(value="Telefone")
  private String telefone;

  @ApiModelProperty(value="Ativo")
  private Boolean ativo;

  @ApiModelProperty(value="Endereço")
  private EnderecoResponseDTO enderecoDto;
}
