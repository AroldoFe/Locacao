package br.ufrn.imd.locacao.Locacao.repository;

import br.ufrn.imd.locacao.Locacao.domain.Locacao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocacaoRepository extends MongoRepository<Locacao, String> {
}
