package br.ufrn.imd.locacao.Locacao.service;

import br.ufrn.imd.locacao.Locacao.domain.Estoque;
import br.ufrn.imd.locacao.Locacao.domain.Fantasia;
import br.ufrn.imd.locacao.Locacao.domain.Loja;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.repository.LojaRepository;
import br.ufrn.imd.locacao.Locacao.utils.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LojaService {
    private final LojaRepository lojaRepository;
    private final EnderecoService enderecoService;
    private final EstoqueService estoqueService;

    public List<Loja> findAll() {
        return this.lojaRepository.findAll();
    }

    public List<Loja> findAllContainsFantasia(Fantasia fantasia) {
        List<Estoque> estoques = this.estoqueService.findByFantasia(fantasia);

        return estoques.stream().map(Estoque::getLoja).collect(Collectors.toList());
    }

    public Loja findById(String id) {
        return this.lojaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));
    }

    public Loja findByCnpj(String cnpj) {
        return this.lojaRepository.findByCnpj(cnpj).orElse(null);
    }

    @Transactional
    public Loja save(Loja loja) throws BusinessRuleException {
        this.validate(loja);
        loja.setDataUltimaEdicao(new Date());
        return this.lojaRepository.save(loja);
    }

    private void validate(Loja loja) throws BusinessRuleException {
        if (ValidatorUtils.isEmpty(loja)) {
            throw new BusinessRuleException("Loja não pode estar vazia");
        }

        List<String> mensagens = new ArrayList<>();

        if (ValidatorUtils.isEmpty(loja.getNome())) {
            mensagens.add("Nome não pode ser vazio");
        }

        if (ValidatorUtils.isEmpty(loja.getCnpj())) {
            mensagens.add("CNPJ não pode ser vazio");
        } else {
            Loja lojaBd = this.findByCnpj(loja.getCnpj());

            if (ValidatorUtils.isNotEmpty(lojaBd) &&
                    (ValidatorUtils.isEmpty(loja.getId()) || !loja.getId().equals(lojaBd.getId()))) {
                mensagens.add("CNPJ em uso");
            }
        }

        this.enderecoService.validate(loja.getEndereco());

        if (!mensagens.isEmpty()) {
            throw new BusinessRuleException(mensagens);
        }
    }
}
