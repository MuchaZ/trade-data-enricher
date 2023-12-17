package pl.arciemowicz.tradedataenricher.controller;

import static pl.arciemowicz.tradedataenricher.util.CSVUtil.convertToCSV;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.arciemowicz.tradedataenricher.service.TradeEnrichmentServiceImpl;

@RestController
@RequiredArgsConstructor
public class TradeEnrichmentController {

    private final TradeEnrichmentServiceImpl service;

    @PostMapping("/api/v1/enrich")
    public ResponseEntity<String> enrichTrades() {
        return ResponseEntity.ok(convertToCSV(service.enrichTrades()));
    }
}

