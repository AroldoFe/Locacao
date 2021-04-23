package br.ufrn.imd.locacao.Locacao.controller.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DevolucaoDTO {
    private String idAluguel;
    private Date dataDevolucao;
}
