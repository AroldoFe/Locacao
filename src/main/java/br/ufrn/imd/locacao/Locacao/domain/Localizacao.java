package br.ufrn.imd.locacao.Locacao.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Localizacao {
    private String type = "Point";
    private List<Double> coordinates = new ArrayList<>(2);
}
