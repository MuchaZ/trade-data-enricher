package pl.arciemowicz.tradedataenricher.service;

import java.util.List;
import pl.arciemowicz.tradedataenricher.dto.EnrichedTrade;

public interface TradeEnrichmentService {
    List<EnrichedTrade> enrichTrades();
}
