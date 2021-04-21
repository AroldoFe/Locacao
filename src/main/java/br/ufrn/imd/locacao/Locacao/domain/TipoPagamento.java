package br.ufrn.imd.locacao.Locacao.domain;

import lombok.Getter;

@Getter
public enum TipoPagamento {
    DINHEIRO(0), CREDITO_UMA_VEZ(0.05f), CREDITO_PARCELADO(0.19f), DEBITO(0.02f), CHEQUE(0);

    private final float juros;

    TipoPagamento(float juros) {
        this.juros = juros;
    }
}
