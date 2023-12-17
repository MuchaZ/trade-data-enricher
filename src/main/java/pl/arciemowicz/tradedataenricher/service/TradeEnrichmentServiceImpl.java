package pl.arciemowicz.tradedataenricher.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.arciemowicz.tradedataenricher.dto.EnrichedTrade;
import pl.arciemowicz.tradedataenricher.dto.Trade;

@Service
@RequiredArgsConstructor
public class TradeEnrichmentServiceImpl implements TradeEnrichmentService {

    private static final Logger logger = LoggerFactory.getLogger(TradeEnrichmentServiceImpl.class);
    private final ProductService productService;
    private final TradeService tradeService;

    @Override
    public List<EnrichedTrade> enrichTrades() {
        return tradeService.getTrades().stream()
                .filter(this::isValidDate)
                .map(this::enrichTrade)
                .collect(Collectors.toList());
    }

    private boolean isValidDate(Trade trade) {
        try {
            LocalDate.parse(trade.getDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            return true;
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: {}", trade.getDate());
            return false;
        }
    }

    private EnrichedTrade enrichTrade(Trade trade) {
        return EnrichedTrade.builder()
                .date(trade.getDate())
                .productName(productService.getProductName(trade.getProductId()))
                .currency(trade.getCurrency())
                .price(trade.getPrice())
                .build();
    }
}
