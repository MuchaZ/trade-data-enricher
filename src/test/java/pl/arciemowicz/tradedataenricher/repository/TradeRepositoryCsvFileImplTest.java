package pl.arciemowicz.tradedataenricher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.test.util.ReflectionTestUtils;
import pl.arciemowicz.tradedataenricher.dto.Trade;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TradeRepositoryCsvFileImplTest {

    private TradeRepositoryCsvFileImpl tradeRepository;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tradeRepository = new TradeRepositoryCsvFileImpl();
        Path tempFile = createSampleCsvFile();
        ReflectionTestUtils.setField(tradeRepository, "path", tempFile.toString());
    }

    @Test
    void findAll_ShouldReturnListOfTrades() {
        List<Trade> trades = tradeRepository.findAll();

        assertEquals(2, trades.size());
        assertEquals("20230101", trades.get(0).getDate());
        assertEquals(1, trades.get(0).getProductId());
        assertEquals("USD", trades.get(0).getCurrency());
        assertEquals(100.0, trades.get(0).getPrice());

        assertEquals("20230102", trades.get(1).getDate());
        assertEquals(2, trades.get(1).getProductId());
        assertEquals("EUR", trades.get(1).getCurrency());
        assertEquals(200.0, trades.get(1).getPrice());
    }

    @Test
    void findAll_ShouldReturnEmptyListWhenNoTradesFound() throws IOException {
        Path emptyFile = createEmptyCsvFile();
        ReflectionTestUtils.setField(tradeRepository, "path", emptyFile.toString());

        List<Trade> trades = tradeRepository.findAll();

        assertTrue(trades.isEmpty());
    }

    private Path createSampleCsvFile() throws IOException {
        Path file = Files.createFile(tempDir.resolve("sample_trades.csv"));
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("Date,ProductId,Currency,Price\n");
            writer.write("20230101,1,USD,100.0\n");
            writer.write("20230102,2,EUR,200.0\n");
        }
        return file;
    }

    private Path createEmptyCsvFile() throws IOException {
        return Files.createFile(tempDir.resolve("empty.csv"));
    }

}
