package br.ufrn.imd.locacao.Locacao.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
public class Fantasia {
    @Id
    private String id;
    private String descricao;

    private TipoTamanho tamanho;
    private TipoClassificacao classificacao;

    private Date dataCriado = new Date();
    private Date dataUltimaEdicao = new Date();
}
