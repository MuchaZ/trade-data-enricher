package pl.arciemowicz.tradedataenricher.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.arciemowicz.tradedataenricher.dto.Trade;
import pl.arciemowicz.tradedataenricher.repository.TradeRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TradeServiceImplTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    void getTrades_ShouldReturnEmptyListWhenNoTradesFound() {
        when(tradeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Trade> result = tradeService.getTrades();

        assertEquals(0, result.size());
    }

    @Test
    void getTrades_ShouldReturnListOfTrades() {
        final String date1 = "20230101";
        final int productId1 = 1;
        final String currency1 = "USD";
        final double price1 = 100.0;

        final String date2 = "20230102";
        final int productId2 = 2;
        final String currency2 = "EUR";
        final double price2 = 200.0;

        Trade trade1 = new Trade(date1, productId1, currency1, price1);
        Trade trade2 = new Trade(date2, productId2, currency2, price2);

        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade1, trade2));

        List<Trade> result = tradeService.getTrades();

        assertEquals(2, result.size());
        assertEquals(trade1, result.get(0));
        assertEquals(trade2, result.get(1));
    }
}
