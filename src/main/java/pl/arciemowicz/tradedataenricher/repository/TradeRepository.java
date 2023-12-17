package pl.arciemowicz.tradedataenricher.repository;

import java.util.List;
import pl.arciemowicz.tradedataenricher.dto.Trade;

public interface TradeRepository {
    List<Trade> findAll();
}
