package pl.arciemowicz.tradedataenricher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trade {
    private String date;
    private int productId;
    private String currency;
    private double price;
}

