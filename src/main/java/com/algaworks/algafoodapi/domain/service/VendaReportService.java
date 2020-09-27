package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiasrias(VendaDiariaFilter filtro, String timeOffset);
}
