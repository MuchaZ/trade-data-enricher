package pl.arciemowicz.tradedataenricher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.arciemowicz.tradedataenricher.dto.Product;
import pl.arciemowicz.tradedataenricher.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public String getProductName(int productId) {
        return productRepository.findBy(productId)
                .orElse(new Product(productId, "Missing Product Name"))
                .getProductName();
    }
}
