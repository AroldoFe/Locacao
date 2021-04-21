package br.ufrn.imd.locacao.Locacao.repository;

import br.ufrn.imd.locacao.Locacao.domain.Estoque;
import br.ufrn.imd.locacao.Locacao.domain.Fantasia;
import br.ufrn.imd.locacao.Locacao.domain.Loja;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends MongoRepository<Estoque, String> {
    Optional<Estoque> findByLoja(Loja loja);

    List<Estoque> findAllByFantasiasContains(Fantasia fantasia);
}
