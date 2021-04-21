package br.ufrn.imd.locacao.Locacao.controller;

import br.ufrn.imd.locacao.Locacao.domain.Estoque;
import br.ufrn.imd.locacao.Locacao.domain.Fantasia;
import br.ufrn.imd.locacao.Locacao.domain.Loja;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/estoque")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstoqueRestController {
    private final EstoqueService estoqueService;

    @GetMapping
    public Estoque findByLoja(@RequestParam("loja") Loja loja) {
        return this.estoqueService.findByLoja(loja);
    }

    @PostMapping(path = "/addFantasia")
    public Estoque addFantasia(@RequestParam("loja") Loja loja, @RequestParam("fantasia") Fantasia fantasia) throws BusinessRuleException {
        return this.estoqueService.addFantasia(loja, fantasia);
    }
}
