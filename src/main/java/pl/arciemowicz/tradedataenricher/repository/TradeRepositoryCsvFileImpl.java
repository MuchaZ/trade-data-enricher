package pl.arciemowicz.tradedataenricher.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.arciemowicz.tradedataenricher.dto.Trade;

@Repository
public class TradeRepositoryCsvFileImpl implements TradeRepository {

    @Value("${trades.file.path}")
    private String path;
    private List<Trade> trades;

    private void loadTrades() {
        this.trades = new ArrayList<>();
        try {
            readTradesFromFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTradesFromFile(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            skipFirstLine(br);
            String line;
            while ((line = br.readLine()) != null) {
                Trade trade = parseTradeFromLine(line);
                trades.add(trade);
            }
        }
    }

    private void skipFirstLine(BufferedReader br) throws IOException {
        br.readLine();
    }

    private Trade parseTradeFromLine(String line) {
        String[] values = line.split(",");
        return new Trade(values[0], Integer.parseInt(values[1]), values[2], Double.parseDouble(values[3]));
    }

    @Override
    public List<Trade> findAll() {
        if (trades == null) {
            loadTrades();
        }
        return this.trades;
    }
}
