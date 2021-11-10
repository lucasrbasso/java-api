package com.gvendas.gestaovendas.dto.cliente;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel("Endereço requisição DTO")
public class EnderecoRequestDTO {

    @Length(min = 3, max = 30, message = "Logradouro")
    @NotBlank(message = "Logradouro")
    @ApiModelProperty(value="Logradouro")
    private String logradouro;

    @NotNull(message = "Número")
    @ApiModelProperty(value="Numero")
    private Integer numero;

    @Length(max = 30, message = "Complemento")
    @ApiModelProperty(value="Complemento")
    private String complemento;

    @Length(min = 3, max = 30, message = "Bairro")
    @NotBlank(message = "Bairro")
    @ApiModelProperty(value="Bairro")
    private String bairro;

    @NotBlank(message = "CEP")
    @Pattern(regexp = "[\\d]{5}-[\\d]{3}", message = "CEP")
    @ApiModelProperty(value="CEP")
    private String cep;

    @Length(min = 3, max = 30, message = "Cidade")
    @NotBlank(message = "Cidade")
    @ApiModelProperty(value="Cidade")
    private String cidade;

    @Length(min = 3, max = 30, message = "Estado")
    @NotBlank(message = "Estado")
    @ApiModelProperty(value="Estado")
    private String estado;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}