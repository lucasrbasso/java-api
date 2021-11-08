package com.gvendas.gestaovendas.dto.cliente;

import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.Endereco;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

@ApiModel("Cliente retorno DTO")
public class ClienteResponseDTO {
  @ApiModelProperty(value="Código")
  private Long codigo;

  @ApiModelProperty(value="Nome")
  private String nome;

  @ApiModelProperty(value="Telefone")
  private String telefone;

  @ApiModelProperty(value="Ativo")
  private Boolean ativo;

  @ApiModelProperty(value="Endereço")
  private EnderecoResponseDTO enderecoDto;

  public ClienteResponseDTO(Long codigo, String nome, String telefone, Boolean ativo, EnderecoResponseDTO enderecoDto) {
    this.codigo = codigo;
    this.nome = nome;
    this.telefone = telefone;
    this.ativo = ativo;
    this.enderecoDto = enderecoDto;
  }

  public static ClienteResponseDTO converterParaClienteDTO(Cliente cliente) {
    EnderecoResponseDTO endereco = new EnderecoResponseDTO(
      cliente.getEndereco().getLogradouro(),
      cliente.getEndereco().getNumero(),
      cliente.getEndereco().getComplemento(),
      cliente.getEndereco().getBairro(),
      cliente.getEndereco().getCep(),
      cliente.getEndereco().getCidade(),
      cliente.getEndereco().getEstado());
    return new ClienteResponseDTO(cliente.getCodigo(), cliente.getNome(),cliente.getTelefone(), cliente.getAtivo(), endereco);
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public EnderecoResponseDTO getEnderecoDto() {
    return enderecoDto;
  }

  public void setEnderecoDto(EnderecoResponseDTO enderecoDto) {
    this.enderecoDto = enderecoDto;
  }

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }
}
