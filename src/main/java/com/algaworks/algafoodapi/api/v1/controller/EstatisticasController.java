package com.algaworks.algafoodapi.api.v1.controller;

import com.algaworks.algafoodapi.api.v1.AlgaLinks;
import com.algaworks.algafoodapi.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafoodapi.api.v1.model.dto.VendaDiaria;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algafoodapi.domain.service.VendaQueryService;
import com.algaworks.algafoodapi.domain.service.VendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private VendaReportService vendaReportService;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Estatisticas.PodeConsultar
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        return vendaQueryService.consultaVendasDiarias(filtro, timeOffSet);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    @CheckSecurity.Estatisticas.PodeConsultar
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
                                                            @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {

        byte[] bytesPdf = vendaReportService.emitirVendasDiasrias(filtro, timeOffSet);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Estatisticas.PodeConsultar
    public EstatisticasModel estatisticas() {
        var estatisticasModel = new EstatisticasModel();

        estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));

        return estatisticasModel;
    }

    public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
    }
}
