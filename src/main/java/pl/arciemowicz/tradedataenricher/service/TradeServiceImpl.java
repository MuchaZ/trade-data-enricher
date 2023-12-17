package pl.arciemowicz.tradedataenricher.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.arciemowicz.tradedataenricher.dto.Trade;
import pl.arciemowicz.tradedataenricher.repository.TradeRepository;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Override
    public List<Trade> getTrades() {
        return tradeRepository.findAll();
    }
}
