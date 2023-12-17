package pl.arciemowicz.tradedataenricher.repository;

import java.util.Optional;
import pl.arciemowicz.tradedataenricher.dto.Product;

public interface ProductRepository {
    Optional<Product> findBy(int productId);
}
