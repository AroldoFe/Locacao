package br.ufrn.imd.locacao.Locacao.service;

import br.ufrn.imd.locacao.Locacao.controller.DTO.DevolucaoDTO;
import br.ufrn.imd.locacao.Locacao.domain.*;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.repository.AluguelRepository;
import br.ufrn.imd.locacao.Locacao.utils.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AluguelService {
    private final AluguelRepository aluguelRepository;
    private final ClienteService clienteService;
    private final LojaService lojaService;
    private final EstoqueService estoqueService;

    public List<Aluguel> buscarTodosPorClientes(String cpf) {
        Cliente cliente = clienteService.findByCpf(cpf);
        return this.aluguelRepository.findAllByClienteOrderByDataAluguel(cliente);
    }

    public List<Aluguel> buscarPorClientesENaoDevolvidos(String cpf) {
        Cliente cliente = clienteService.findByCpf(cpf);
        return this.aluguelRepository.findAllByClienteAndDataDevolucaoIsNullOrderByDataAluguel(cliente);
    }

    public List<Aluguel> buscarPorClientesEDevolvidos(String cpf) {
        Cliente cliente = clienteService.findByCpf(cpf);
        return this.aluguelRepository.findAllByClienteAndDataDevolucaoIsNotNullOrderByDataAluguel(cliente);
    }

    public List<Aluguel> buscarTodosPorLojas(String cnpj) {
        Loja loja = lojaService.findByCnpj(cnpj);
        return this.aluguelRepository.findAllByLojaOrderByDataAluguel(loja);
    }

    public List<Aluguel> buscarPorLojasENaoDevolvidos(String cnpj) {
        Loja loja = lojaService.findByCnpj(cnpj);
        return this.aluguelRepository.findAllByLojaAndDataDevolucaoIsNullOrderByDataAluguel(loja);
    }

    public List<Aluguel> buscarPorLojasEDevolvidos(String cnpj) {
        Loja loja = lojaService.findByCnpj(cnpj);
        return  this.aluguelRepository.findAllByLojaAndDataDevolucaoIsNotNullOrderByDataAluguel(loja);
    }

    public Double valorGastoTotalDaLoja(String cnpj) {
        List<Aluguel> listaAlugueis = this.buscarTodosPorLojas(cnpj);
        return Double.valueOf(new DecimalFormat("#.##").format(listaAlugueis.stream().mapToDouble(Aluguel::getTotal).sum()));
    }

    public Double valorGastoPendenteDaLoja(String cnpj) {
        List<Aluguel> listaAlugueis = this.buscarPorLojasENaoDevolvidos(cnpj);
        return Double.valueOf(new DecimalFormat("#.##").format(listaAlugueis.stream().mapToDouble(Aluguel::getTotal).sum()));
    }

    public Double valorGastoDevolvidoDaLoja(String cnpj) {
        List<Aluguel> listaAlugueis = this.buscarPorLojasEDevolvidos(cnpj);
        return Double.valueOf(new DecimalFormat("#.##").format(listaAlugueis.stream().mapToDouble(Aluguel::getTotal).sum()));
    }

    public Map<Loja, Double> valorGastoTotalDoCliente(String cpf) {
        List<Aluguel> listaAlugueis = this.buscarTodosPorClientes(cpf);
        return listaAlugueis.stream().collect(
                Collectors.groupingBy(Aluguel::getLoja, Collectors.summingDouble(Aluguel::getTotal)));

    }

    public Map<Loja, Double> valorGastoPendenteDoCliente(String cpf) {
        List<Aluguel> listaAlugueis = this.buscarPorClientesENaoDevolvidos(cpf);
        return listaAlugueis.stream().collect(
                Collectors.groupingBy(Aluguel::getLoja, Collectors.summingDouble(Aluguel::getTotal))
        );
    }

    public Map<Loja, Double> valorGastoDevolvidoDoCliente(String cpf) {
        List<Aluguel> listaAlugueis = this.buscarPorClientesEDevolvidos(cpf);
        return listaAlugueis.stream().collect(
                Collectors.groupingBy(Aluguel::getLoja, Collectors.summingDouble(Aluguel::getTotal))
        );
    }


    @Transactional
    public Aluguel salvar(Aluguel aluguel) throws BusinessRuleException {
        Estoque estoqueLojaFantasia = this.estoqueService.findByLoja(aluguel.getLoja());
        if (estoqueLojaFantasia.getFantasias().stream().noneMatch(Predicate.isEqual(aluguel.getFantasia()))) {
            throw new BusinessRuleException("Não existe essa fantasia na loja");
        }

        if (!this.aluguelRepository.findAllByFantasiaAndLojaAndDataDevolucaoIsNotNull(
                aluguel.getFantasia(),
                aluguel.getLoja()).isEmpty()
        ) {
            throw new BusinessRuleException("Essa fantasia já está alugada nesta loja");
        }

        this.validarAluguel(aluguel);
        aluguel.setDataDevolucao(null);
        aluguel.setTotal(Double.valueOf(new DecimalFormat("#.##")
                .format(aluguel.getValor() * (1 + aluguel.getPagamento().getJuros())))
        );

        return this.aluguelRepository.save(aluguel);
    }

    @Transactional
    public Aluguel devolver(DevolucaoDTO devolucaoDTO) throws BusinessRuleException {
        Aluguel aluguel = this.aluguelRepository.findById(devolucaoDTO.getIdAluguel())
                .orElseThrow(() -> new ResourceNotFoundException("Locação não encontrado"));

        this.validarDevolucao(devolucaoDTO, aluguel);
        aluguel.setSituacaoPagamento(TipoSituacaoPagamento.PAGO);

        long qtdDiasDiferencaoNormal = ChronoUnit.DAYS.between(
                aluguel.getDataAluguel().toInstant(),
                devolucaoDTO.getDataDevolucao().toInstant());
        aluguel.setTotal(aluguel.getTotal() * qtdDiasDiferencaoNormal);

        if (devolucaoDTO.getDataDevolucao().after(aluguel.getDataMaximaDevolucao())) {
            long qtdDiasDiferencaJuros = ChronoUnit.DAYS.between(
                    aluguel.getDataMaximaDevolucao().toInstant(),
                    devolucaoDTO.getDataDevolucao().toInstant());
            aluguel.setTotal(aluguel.getTotal() + (aluguel.getValor() * (qtdDiasDiferencaJuros * 0.1)));
        }

        aluguel.setTotal(Double.valueOf(new DecimalFormat("#.##").format(aluguel.getTotal())));

        aluguel.setDataDevolucao(devolucaoDTO.getDataDevolucao());

        return this.aluguelRepository.save(aluguel);
    }

    private void validarAluguel(Aluguel aluguel) throws BusinessRuleException {
        List<String> mensagens = new ArrayList<>();

        if (ValidatorUtils.isEmpty(aluguel)) {
            throw new BusinessRuleException("A locação da fantasia não pode ser vázia");
        }

        if (ValidatorUtils.isEmpty(aluguel.getCliente())) {
            mensagens.add("O aluguel não está vinculado a nenhum cliente");
        }

        if (ValidatorUtils.isEmpty(aluguel.getLoja())) {
            mensagens.add("O aluguel não está vinculado a nenhuma loja");
        }

        if (ValidatorUtils.isEmpty(aluguel.getFantasia())) {
            mensagens.add("O aluguel não está vinculado a nenhuma fantasia");
        }

        if (ValidatorUtils.isEmpty(aluguel.getSituacaoPagamento())) {
            mensagens.add("O aluguel não possui uma situacao de pagamento");
        } else {
            if (Arrays.stream(TipoSituacaoPagamento.values()).noneMatch(Predicate.isEqual(aluguel.getSituacaoPagamento()))) {
                mensagens.add("O tipo de situação de pagamento não é valido");
            }
        }

        if (ValidatorUtils.isEmpty(aluguel.getPagamento())) {
            mensagens.add("O aluguel não possui nehum pagamento");
        } else {
            if (Arrays.stream(TipoPagamento.values()).noneMatch(Predicate.isEqual(aluguel.getPagamento()))) {
                mensagens.add("O tipo de pagamento não é valido");
            }
        }

        if (ValidatorUtils.isEmpty(aluguel.getValor())) {
            mensagens.add("O aluguel não possui nenhum valor");
        }

        if (ValidatorUtils.isEmpty(aluguel.getDataAluguel())) {
            mensagens.add("O aluguel não possui nenhuma data de locação");
        }

        if (ValidatorUtils.isEmpty(aluguel.getDataMaximaDevolucao())) {
            mensagens.add("O aluguel não possui nenhuma data máxima de locação");
        }

        if (!mensagens.isEmpty()) {
            throw new BusinessRuleException(mensagens);
        }
    }

    private void validarDevolucao(DevolucaoDTO devolucaoDTO, Aluguel aluguel) throws BusinessRuleException {
        if (ValidatorUtils.isNotEmpty(aluguel.getDataDevolucao()) || aluguel.getDataDevolucao() != null) {
            throw new BusinessRuleException("Não pode devolver um produto já devolvido");
        }

        if (devolucaoDTO.getDataDevolucao().before(aluguel.getDataAluguel())) {
            throw new BusinessRuleException("Data de devolução não pode ser antes que a data de aluguel");
        }
    }
}
