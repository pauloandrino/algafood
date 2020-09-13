package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.api.model.dto.VendaDiaria;
import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filtro, String timeOffSet);
}
