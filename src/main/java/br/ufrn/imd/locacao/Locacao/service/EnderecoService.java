package br.ufrn.imd.locacao.Locacao.service;

import br.ufrn.imd.locacao.Locacao.domain.Endereco;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.utils.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    public void validate(Endereco endereco) throws BusinessRuleException {
        List<String> mensagens = new ArrayList<>();

        if (ValidatorUtils.isEmpty(endereco)) {
            throw new BusinessRuleException("Endereço não pode ser vazio");
        }

        if (ValidatorUtils.isEmpty(endereco.getCep())) {
            mensagens.add("CEP não pode ser vazio");
        }

        if (ValidatorUtils.isEmpty(endereco.getLogradouro())) {
            mensagens.add("Logradouro não pode ser vazio");
        }

        if (ValidatorUtils.isEmpty(endereco.getNumero())) {
            mensagens.add("Número não pode ser vazio");
        }

        if (ValidatorUtils.isEmpty(endereco.getUf())) {
            mensagens.add("UF não pode ser vazia");
        }

        if (ValidatorUtils.isEmpty(endereco.getCidade())) {
            mensagens.add("Cidade não pode ser vazia");
        }

        if (ValidatorUtils.isNotEmpty(mensagens)) {
            throw new BusinessRuleException(mensagens);
        }
    }
}
