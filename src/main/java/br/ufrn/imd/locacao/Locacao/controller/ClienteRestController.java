package br.ufrn.imd.locacao.Locacao.controller;

import br.ufrn.imd.locacao.Locacao.domain.Cliente;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cliente")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClienteRestController {
    private final ClienteService clienteService;

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
}
