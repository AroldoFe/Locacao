package br.ufrn.imd.locacao.Locacao.controller;

import br.ufrn.imd.locacao.Locacao.domain.Locacao;
import br.ufrn.imd.locacao.Locacao.repository.LocacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locacao")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LocacaoRestController {
    private final LocacaoRepository locacaoRepository;

    @GetMapping
    public List<Locacao> getAll() {
        return this.locacaoRepository.findAll();
    }

    @PostMapping
    public Locacao save(@RequestBody Locacao locacao) {
        return this.locacaoRepository.save(locacao);
    }
}
