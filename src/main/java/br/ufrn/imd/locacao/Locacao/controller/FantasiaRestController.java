package br.ufrn.imd.locacao.Locacao.controller;

import br.ufrn.imd.locacao.Locacao.domain.Fantasia;
import br.ufrn.imd.locacao.Locacao.exception.BusinessRuleException;
import br.ufrn.imd.locacao.Locacao.service.FantasiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fantasia")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class FantasiaRestController {
    private final FantasiaService fantasiaService;

    @GetMapping
    public List<Fantasia> findAll() {
        return this.fantasiaService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Fantasia findById(@PathVariable("id") String id) {
        return this.fantasiaService.findById(id);
    }

    @PostMapping
    public Fantasia save(@RequestBody Fantasia fantasia) throws BusinessRuleException {
        return this.fantasiaService.save(fantasia);
    }
}
