package br.ufrn.imd.locacao.Locacao.repository;

import br.ufrn.imd.locacao.Locacao.domain.Fantasia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FantasiaRepository extends MongoRepository<Fantasia, String> {
}
