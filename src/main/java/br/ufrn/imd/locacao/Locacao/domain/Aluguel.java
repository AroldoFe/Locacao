package br.ufrn.imd.locacao.Locacao.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
public class Aluguel {
    @Id
    private String id;

    @DBRef
    private Cliente cliente;
    @DBRef
    private Loja loja;
    @DBRef
    private Fantasia fantasia;

    private TipoSituacaoPagamento situacaoPagamento;
    private TipoPagamento pagamento;

    private Double valor;
    private Double total;

    private Date dataAluguel;
    private Date dataMaximaDevolucao;
    private Date dataDevolucao;
}
