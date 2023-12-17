package pl.arciemowicz.tradedataenricher.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.arciemowicz.tradedataenricher.dto.EnrichedTrade;
import pl.arciemowicz.tradedataenricher.service.TradeEnrichmentServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static pl.arciemowicz.tradedataenricher.util.CSVUtil.convertToCSV;

@ExtendWith(MockitoExtension.class)
class TradeEnrichmentControllerTest {

    @Mock
    private TradeEnrichmentServiceImpl service;

    @InjectMocks
    private TradeEnrichmentController controller;

    @Test
    void enrichTrades_ShouldReturnCSVOfEnrichedTrades() {
        EnrichedTrade trade1 = EnrichedTrade.builder()
                .date("20230101")
                .productName("Product 1")
                .currency("USD")
                .price(100.0)
                .build();
        EnrichedTrade trade2 = EnrichedTrade.builder()
                .date("20230102")
                .productName("Product 2")
                .currency("EUR")
                .price(150.0)
                .build();
        List<EnrichedTrade> enrichedTrades = Arrays.asList(trade1, trade2);
        when(service.enrichTrades()).thenReturn(enrichedTrades);

        ResponseEntity<String> response = controller.enrichTrades();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(convertToCSV(enrichedTrades), response.getBody());
    }

}
