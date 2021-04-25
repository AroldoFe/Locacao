package br.ufrn.imd.locacao.Locacao.controller;

import br.ufrn.imd.locacao.Locacao.domain.Cliente;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.service.AluguelService;
import br.ufrn.imd.locacao.Locacao.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public Map<String, Double> valorGastoTotal(@RequestParam("cpf") String cpf) {
        Map<String, Double> valorGastoPorLoja = new HashMap<>();
        this.aluguelService.valorGastoTotalDoCliente(cpf).forEach((key, value) -> valorGastoPorLoja.put(key.getNome(), value));
        return valorGastoPorLoja;
    }

    @GetMapping("/valorGastoPendente")
    public Map<String, Double> valorGastoPendente(@RequestParam("cpf") String cpf) {
        Map<String, Double> valorPendente = new HashMap<>();
        this.aluguelService.valorGastoPendenteDoCliente(cpf).forEach((key, value) -> valorPendente.put(key.getNome(), value));
        return valorPendente;
    }

    @GetMapping("/valorGastoDevolvido")
    public Map<String, Double> valorGastoDevolvido(@RequestParam("cpf") String cpf) {
        HashMap<String, Double> valorDevolvido = new HashMap<>();
        this.aluguelService.valorGastoDevolvidoDoCliente(cpf).forEach((key, value) -> valorDevolvido.put(key.getNome(), value));
        return valorDevolvido;
    }
}
