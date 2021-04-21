package br.ufrn.imd.locacao.Locacao.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Loja {
    @Id
    private String id;
    private String nome;
    private String cnpj;

    @CreatedDate
    private Date dataCriacao = new Date();
    @LastModifiedDate
    private Date dataUltimaEdicao = new Date();

    private Localizacao localizacao;
    private Endereco endereco;
}
