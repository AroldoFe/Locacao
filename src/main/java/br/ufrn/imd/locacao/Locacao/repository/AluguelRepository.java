package br.ufrn.imd.locacao.Locacao.repository;

import br.ufrn.imd.locacao.Locacao.domain.Aluguel;
import br.ufrn.imd.locacao.Locacao.domain.Cliente;
import br.ufrn.imd.locacao.Locacao.domain.Loja;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AluguelRepository extends MongoRepository<Aluguel, String> {
    List<Aluguel> findAllByLojaOrderByDataAluguel(Loja loja);

    /**
     * Acha os Alugueis feitos que não foram devolvidos ainda
     *
     * @param loja - Loja
     * @return Alugueis não devolvidos aindaa
     */
    List<Aluguel> findAllByLojaAndDataDevolucaoIsNull(Loja loja);

    List<Aluguel> findAllByClienteOrderByDataAluguel(Cliente cliente);
}
