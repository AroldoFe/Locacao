package br.ufrn.imd.locacao.Locacao.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Estoque {
    @Id
    private String id;

    @DBRef
    private Loja loja;

    @DBRef
    private List<Fantasia> fantasias = new ArrayList<>();
}
