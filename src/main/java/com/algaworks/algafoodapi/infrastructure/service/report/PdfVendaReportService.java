package com.algaworks.algafoodapi.infrastructure.service.report;

import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algafoodapi.domain.service.VendaReportService;
import org.springframework.stereotype.Service;

@Service
public class PdfVendaReportService implements VendaReportService {

    @Override
    public byte[] emitirVendasDiasrias(VendaDiariaFilter filtro, String timeOffset) {
        return null;
    }
}
