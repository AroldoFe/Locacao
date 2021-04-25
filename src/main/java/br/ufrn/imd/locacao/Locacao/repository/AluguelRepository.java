package br.ufrn.imd.locacao.Locacao.repository;

import br.ufrn.imd.locacao.Locacao.domain.Aluguel;
import br.ufrn.imd.locacao.Locacao.domain.Cliente;
import br.ufrn.imd.locacao.Locacao.domain.Fantasia;
import br.ufrn.imd.locacao.Locacao.domain.Loja;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AluguelRepository extends MongoRepository<Aluguel, String> {
    /**
     * Acha todos os Alugueis feitos na loja
     *
     * @param loja - Loja
     * @return Alugueis não devolvidos aindaa
     */

    List<Aluguel> findAllByLojaOrderByDataAluguel(Loja loja);

    /**
     * Acha os Alugueis feitos que não foram devolvidos ainda
     *
     * @param loja - Loja
     * @return Alugueis não devolvidos aindaa
     */
    List<Aluguel> findAllByLojaAndDataDevolucaoIsNullOrderByDataAluguel(Loja loja);

    /**
     * Acha os alugueis feitos que já foram devolvidos a loja
     *
     * @param loja - Loja
     * @return Alugueis não devolvidos aindaa
     */
    List<Aluguel> findAllByLojaAndDataDevolucaoIsNotNullOrderByDataAluguel(Loja loja);

    List<Aluguel> findAllByClienteOrderByDataAluguel(Cliente cliente);

    List<Aluguel> findAllByClienteAndDataDevolucaoIsNullOrderByDataAluguel(Cliente cliente);

    List<Aluguel> findAllByClienteAndDataDevolucaoIsNotNullOrderByDataAluguel(Cliente cliente);

    List<Aluguel> findAllByFantasiaAndLojaAndDataDevolucaoIsNull(Fantasia fantasia, Loja loja);
}
