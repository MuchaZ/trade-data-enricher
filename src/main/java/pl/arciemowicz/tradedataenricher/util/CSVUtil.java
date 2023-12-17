package pl.arciemowicz.tradedataenricher.util;

import java.util.List;
import pl.arciemowicz.tradedataenricher.dto.EnrichedTrade;

public class CSVUtil {

    private CSVUtil() {
    }

    public static String convertToCSV(List<EnrichedTrade> trade) {
        StringBuilder sb = new StringBuilder();
        sb.append("date,productId,currency,price,productName\n");
        for (EnrichedTrade enrichedTrade : trade) {
            sb.append(enrichedTrade.getDate());
            sb.append(",");
            sb.append(enrichedTrade.getProductName());
            sb.append(",");
            sb.append(enrichedTrade.getCurrency());
            sb.append(",");
            sb.append(enrichedTrade.getPrice());
            sb.append("\n");
        }
        return sb.toString();
    }
}
