package br.ufrn.imd.locacao.Locacao.controller;

import br.ufrn.imd.locacao.Locacao.domain.Fantasia;
import br.ufrn.imd.locacao.Locacao.domain.Loja;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.service.AluguelService;
import br.ufrn.imd.locacao.Locacao.service.LojaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loja")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LojaRestController {
    private final LojaService lojaService;
    private final AluguelService aluguelService;

    @GetMapping
    public List<Loja> getAll() {
        return this.lojaService.findAll();
    }

    @GetMapping(path = {"/fantasias"})
    public List<Loja> findAllContainsFantasia(@RequestParam("fantasia") Fantasia fantasia) {
        return this.lojaService.findAllContainsFantasia(fantasia);
    }

    @GetMapping(path = "/{id}")
    public Loja findById(@PathVariable("id") String id) {
        return this.lojaService.findById(id);
    }

    @GetMapping(path = "/cnpj")
    public Loja findByCnpj(@RequestParam("cnpj") String cnpj) {
        return this.lojaService.findByCnpj(cnpj);
    }

    @PostMapping
    public Loja save(@RequestBody Loja loja) throws BusinessRuleException {
        return this.lojaService.save(loja);
    }

    @GetMapping("/valorGastoTotal")
    public Double valorGastoTotal(@RequestParam("cnpj") String cnpj) {
        return this.aluguelService.valorGastoTotalDaLoja(cnpj);
    }

    @GetMapping("/valorGastoPendente")
    public Double valorGastoPendente(@RequestParam("cnpj") String cnpj) {
        return this.aluguelService.valorGastoPendenteDaLoja(cnpj);
    }

    @GetMapping("/valorGastoDevolvido")
    public Double valorGastoDevolvido(@RequestParam("cnpj") String cnpj) {
        return this.aluguelService.valorGastoDevolvidoDaLoja(cnpj);
    }
}
