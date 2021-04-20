package br.ufrn.imd.locacao.Locacao.service;

import br.ufrn.imd.locacao.Locacao.domain.Cliente;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.repository.ClienteRepository;
import br.ufrn.imd.locacao.Locacao.utils.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService;

    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    public Cliente findById(String id) {
        return this.clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    public Cliente findByCpf(String cpf) {
        return this.clienteRepository.findClienteByCpf(cpf).orElse(null);
    }

    @Transactional
    public Cliente save(Cliente cliente) throws BusinessRuleException {
        this.validate(cliente);
        return this.clienteRepository.save(cliente);
    }

    private void validate(Cliente cliente) throws BusinessRuleException {
        List<String> mensagens = new ArrayList<>();
        if (ValidatorUtils.isEmpty(cliente)) {
            throw new BusinessRuleException("Cliente não pode ser nulo");
        }

        if (ValidatorUtils.isEmpty(cliente.getNome())) {
            mensagens.add("Nome não pode estar vazio");
        }

        if (ValidatorUtils.isEmpty(cliente.getCpf())) {
            mensagens.add("CPF não pode estar vazio");
        } else {
            Cliente clienteBd = this.findByCpf(cliente.getCpf());

            if (ValidatorUtils.isNotEmpty(clienteBd) && (ValidatorUtils.isEmpty(cliente.getId()) || !cliente.getId().equals(clienteBd.getId()))) {
                mensagens.add("CPF em uso");
            }
        }

        if (ValidatorUtils.isEmpty(cliente.getDataNascimento())) {
            mensagens.add("Data de Nascimento não pode estar vazia");
        }

        this.enderecoService.validate(cliente.getEndereco());

        if (ValidatorUtils.isNotEmpty(mensagens)) {
            throw new BusinessRuleException(mensagens);
        }
    }
}
