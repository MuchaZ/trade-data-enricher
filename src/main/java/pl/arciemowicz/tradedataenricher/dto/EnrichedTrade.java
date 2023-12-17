package pl.arciemowicz.tradedataenricher.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrichedTrade {
    private String date;
    private String productName;
    private String currency;
    private double price;
}


