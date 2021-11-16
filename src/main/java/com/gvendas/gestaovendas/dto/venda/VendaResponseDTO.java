package com.gvendas.gestaovendas.dto.venda;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.List;

@ApiModel("Venda retorno DTO")
public class VendaResponseDTO {

  @ApiModelProperty(value="Código")
  private Long codigo;

  @ApiModelProperty(value="Data")
  private LocalDate data;

  @ApiModelProperty(value="Itens da venda")
  private List<ItemVendaResponseDTO> itens;

    public VendaResponseDTO(Long codigo, LocalDate data, List<ItemVendaResponseDTO> itens) {
        this.codigo = codigo;
        this.data = data;
        this.itens = itens;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<ItemVendaResponseDTO> getItemVendaDTOs() {
        return itens;
    }

    public void setItemVendaDTOs(List<ItemVendaResponseDTO> itemVendaDTOs) {
        this.itens = itemVendaDTOs;
    }
}
