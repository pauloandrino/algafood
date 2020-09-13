package com.algaworks.algafoodapi.infrastructure.service;

import com.algaworks.algafoodapi.api.model.dto.VendaDiaria;
import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filtro) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var functionDateDataCriacao = builder.function(
                "date", Date.class, root.get("dataCriacao"));

        var selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
