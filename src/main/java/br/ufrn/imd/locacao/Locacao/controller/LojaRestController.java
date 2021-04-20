package br.ufrn.imd.locacao.Locacao.controller;

import br.ufrn.imd.locacao.Locacao.domain.Loja;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.service.LojaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loja")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LojaRestController {
    private final LojaService lojaService;

    @GetMapping
    public List<Loja> getAll() {
        return this.lojaService.findAll();
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
}
