package br.ufrn.imd.locacao.Locacao.service;

import br.ufrn.imd.locacao.Locacao.domain.Fantasia;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.repository.FantasiaRepository;
import br.ufrn.imd.locacao.Locacao.utils.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FantasiaService {
    private final FantasiaRepository fantasiaRepository;

    public List<Fantasia> findAll() {
        return this.fantasiaRepository.findAll();
    }

    public Fantasia findById(String id) {
        return this.fantasiaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fantasia não encontrada"));
    }

    @Transactional
    public Fantasia save(Fantasia fantasia) throws BusinessRuleException {
        this.validate(fantasia);
        fantasia.setDataUltimaEdicao(new Date());
        return this.fantasiaRepository.save(fantasia);
    }

    private void validate(Fantasia fantasia) throws BusinessRuleException {
        if (ValidatorUtils.isEmpty(fantasia)) {
            throw new BusinessRuleException("Fantasia nao pode estar vazia");
        }

        List<String> mensagens = new ArrayList<>();

        if (ValidatorUtils.isEmpty(fantasia.getDescricao())) {
            mensagens.add("Descrição não pode ser vazia");
        }

        if (ValidatorUtils.isEmpty(fantasia.getTamanho())) {
            mensagens.add("Tamanho não pode ser vazio");
        }

        if (ValidatorUtils.isEmpty(fantasia.getClassificacao())) {
            mensagens.add("Classificacao não pode ser vazia");
        }

        if (!mensagens.isEmpty()) {
            throw new BusinessRuleException(mensagens);
        }
    }
}
