package pl.arciemowicz.tradedataenricher.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.arciemowicz.tradedataenricher.dto.EnrichedTrade;
import pl.arciemowicz.tradedataenricher.dto.Trade;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeEnrichmentServiceImplTest {

    @Mock
    private ProductService productService;

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeEnrichmentServiceImpl tradeEnrichmentService;

    @Test
    void enrichTrades_ShouldEnrichValidTrades() {
        final String validDate = "20230101";
        final int productId = 1;
        final String currency = "USD";
        final double price = 100.0;
        final String productName = "Product 1";

        Trade validTrade = new Trade(validDate, productId, currency, price);
        when(tradeService.getTrades()).thenReturn(Collections.singletonList(validTrade));
        when(productService.getProductName(productId)).thenReturn(productName);

        List<EnrichedTrade> enrichedTrades = tradeEnrichmentService.enrichTrades();

        assertFalse(enrichedTrades.isEmpty());
        EnrichedTrade enrichedTrade = enrichedTrades.get(0);
        assertEquals(validDate, enrichedTrade.getDate());
        assertEquals(productName, enrichedTrade.getProductName());
        assertEquals(currency, enrichedTrade.getCurrency());
        assertEquals(price, enrichedTrade.getPrice());
    }

    @Test
    void enrichTrades_ShouldIgnoreInvalidDateTrades() {
        final String invalidDate = "invalid-date";
        final int productId = 2;
        final String currency = "EUR";
        final double price = 200.0;

        Trade invalidTrade = new Trade(invalidDate, productId, currency, price);
        when(tradeService.getTrades()).thenReturn(Collections.singletonList(invalidTrade));

        List<EnrichedTrade> enrichedTrades = tradeEnrichmentService.enrichTrades();

        assertTrue(enrichedTrades.isEmpty());
    }

    @Test
    void enrichTrades_ShouldHandleEmptyTradeList() {
        when(tradeService.getTrades()).thenReturn(Collections.emptyList());

        List<EnrichedTrade> enrichedTrades = tradeEnrichmentService.enrichTrades();

        assertTrue(enrichedTrades.isEmpty());
    }

    @Test
    void enrichTrades_ShouldProcessMultipleTrades() {
        Trade trade1 = new Trade("20230101", 1, "USD", 100.0);
        Trade trade2 = new Trade("20230102", 2, "EUR", 150.0);
        when(tradeService.getTrades()).thenReturn(Arrays.asList(trade1, trade2));
        when(productService.getProductName(1)).thenReturn("Product 1");
        when(productService.getProductName(2)).thenReturn("Product 2");

        List<EnrichedTrade> enrichedTrades = tradeEnrichmentService.enrichTrades();

        assertEquals(2, enrichedTrades.size());
    }

}
