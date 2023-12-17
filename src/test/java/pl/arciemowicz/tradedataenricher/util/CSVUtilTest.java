package pl.arciemowicz.tradedataenricher.util;

import org.junit.jupiter.api.Test;
import pl.arciemowicz.tradedataenricher.dto.EnrichedTrade;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVUtilTest {

    @Test
    void convertToCSV_ShouldConvertListOfEnrichedTradesToCSVFormat() {
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
        List<EnrichedTrade> trades = Arrays.asList(trade1, trade2);

        String expectedCSV = "date,productId,currency,price,productName\n" +
                "20230101,Product 1,USD,100.0\n" +
                "20230102,Product 2,EUR,150.0\n";

        String actualCSV = CSVUtil.convertToCSV(trades);

        assertEquals(expectedCSV, actualCSV);
    }

}
