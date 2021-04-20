package br.ufrn.imd.locacao.Locacao.repository;

import br.ufrn.imd.locacao.Locacao.domain.Loja;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LojaRepository extends MongoRepository<Loja, String> {
    Optional<Loja> findByCnpj(String cnpj);
}
