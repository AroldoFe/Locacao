package br.ufrn.imd.locacao.Locacao.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {
    private String cep;
    private String logradouro;
    private String numero;
    private String uf;
    private String cidade;
    private String bairro;
}
