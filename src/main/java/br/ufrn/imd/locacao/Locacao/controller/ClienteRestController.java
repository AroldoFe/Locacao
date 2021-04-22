package br.ufrn.imd.locacao.Locacao.controller;

import br.ufrn.imd.locacao.Locacao.domain.Cliente;
import br.ufrn.imd.locacao.Locacao.domain.Loja;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.service.AluguelService;
import br.ufrn.imd.locacao.Locacao.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cliente")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClienteRestController {
    private final ClienteService clienteService;
    private final AluguelService aluguelService;

    @GetMapping
    public List<Cliente> getAll() {
        return this.clienteService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Cliente findById(@PathVariable("id") String id) {
        return this.clienteService.findById(id);
    }

    @GetMapping(path = "/cpf")
    public Cliente findByCpf(@RequestParam("cpf") String cpf) {
        return this.clienteService.findByCpf(cpf);
    }

    @PostMapping
    public Cliente save(@RequestBody Cliente cliente) throws BusinessRuleException {
        return this.clienteService.save(cliente);
    }

    @GetMapping("/valorGastoTotal")
    public Map<Loja, Double> valorGastoTotal(@RequestParam("cpf") String cpf) {
        return this.aluguelService.valorGastoTotalDoCliente(cpf);
    }

    @GetMapping("/valorGastoPendente")
    public Map<Loja, Double> valorGastoPendente(@RequestParam("cpf") String cpf) {
        return this.aluguelService.valorGastoPendenteDoCliente(cpf);
    }

    @GetMapping("/valorGastoDevolvido")
    public Map<Loja, Double> valorGastoDevolvido(@RequestParam("cpf") String cpf) {
        return this.aluguelService.valorGastoDevolvidoDoCliente(cpf);
    }
}
