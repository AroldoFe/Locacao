package br.ufrn.imd.locacao.Locacao.controller;

import br.ufrn.imd.locacao.Locacao.controller.DTO.DevolucaoDTO;
import br.ufrn.imd.locacao.Locacao.domain.Aluguel;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.service.AluguelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aluguel")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AluguelRestController {
    private final AluguelService aluguelService;

    @GetMapping("/cliente")
    public List<Aluguel> findAllByCliente(@RequestParam("cpf") String cpf) { return this.aluguelService.buscarTodosPorClientes(cpf);}

    @GetMapping("/clienteNaoDevolvido")
    public List<Aluguel> findByClienteAndNotDevolucao(@RequestParam("cpf") String cpf) { return this.aluguelService.buscarPorClientesENaoDevolvidos(cpf);}

    @GetMapping("/loja")
    public List<Aluguel> findAllByLoja(@RequestParam("cnpj") String cnpj) { return this.aluguelService.buscarTodosPorLojas(cnpj);}

    @GetMapping("/lojaNaoDevolvidos")
    public List<Aluguel> findByLojaAndNotDevolucao(@RequestParam("cnpj") String cnpj) { return this.aluguelService.buscarPorLojasENaoDevolvidos(cnpj);}

    @PostMapping("/alugar")
    public Aluguel alugar(@RequestBody Aluguel aluguel) throws BusinessRuleException {
        return this.aluguelService.salvar(aluguel);
    }

    @PostMapping("/devolucao")
    public Aluguel devolver(@RequestBody DevolucaoDTO devolucaoDTO) throws BusinessRuleException {
        return this.aluguelService.devolver(devolucaoDTO);
    }

}
