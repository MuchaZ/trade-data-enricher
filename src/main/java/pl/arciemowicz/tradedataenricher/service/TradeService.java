package pl.arciemowicz.tradedataenricher.service;

import java.util.List;
import pl.arciemowicz.tradedataenricher.dto.Trade;

public interface TradeService {
    List<Trade> getTrades();
}
