package br.ufrn.imd.locacao.Locacao.repository;

import br.ufrn.imd.locacao.Locacao.domain.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClienteRepository extends MongoRepository<Cliente, String> {

    Optional<Cliente> findClienteByCpf(String cpf);
}
