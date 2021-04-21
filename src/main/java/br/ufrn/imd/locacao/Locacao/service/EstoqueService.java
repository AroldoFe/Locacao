package br.ufrn.imd.locacao.Locacao.service;

import br.ufrn.imd.locacao.Locacao.domain.Estoque;
import br.ufrn.imd.locacao.Locacao.domain.Fantasia;
import br.ufrn.imd.locacao.Locacao.domain.Loja;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.repository.EstoqueRepository;
import br.ufrn.imd.locacao.Locacao.utils.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;

    public List<Estoque> findByFantasia(Fantasia fantasia) {
        return this.estoqueRepository.findAllByFantasiasContains(fantasia);
    }

    public Estoque findByLoja(Loja loja) throws ResourceNotFoundException {
        return this.estoqueRepository.findByLoja(loja).orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado"));
    }

    @Transactional
    public Estoque addFantasia(Loja loja, Fantasia fantasia) throws BusinessRuleException {
        Estoque estoque;
        try {
            estoque = this.findByLoja(loja);
        } catch (ResourceNotFoundException ignored) {
            estoque = new Estoque();
            estoque.setLoja(loja);
        }

        estoque.getFantasias().add(fantasia);

        return this.save(estoque);
    }

    private Estoque save(Estoque estoque) throws BusinessRuleException {
        this.validate(estoque);
        return this.estoqueRepository.save(estoque);
    }

    private void validate(Estoque estoque) throws BusinessRuleException {
        if (ValidatorUtils.isEmpty(estoque)) {
            throw new BusinessRuleException("Estoque não pode estar vazio");
        }

        List<String> mensagens = new ArrayList<>();

        if (ValidatorUtils.isEmpty(estoque.getLoja())) {
            mensagens.add("Estoque não associado a uma loja");
        }

        if (ValidatorUtils.isEmpty(estoque.getFantasias())) {
            mensagens.add("Estoque sem fantasias");
        }

        if (!mensagens.isEmpty()) {
            throw new BusinessRuleException(mensagens);
        }
    }
}
