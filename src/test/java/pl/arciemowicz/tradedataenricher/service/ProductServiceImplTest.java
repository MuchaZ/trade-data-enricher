package pl.arciemowicz.tradedataenricher.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.arciemowicz.tradedataenricher.dto.Product;
import pl.arciemowicz.tradedataenricher.repository.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getProductName_ShouldReturnProductName() {
        final int productId = 1;
        final String expectedProductName = "Product Name";

        Product product = new Product(productId, expectedProductName);
        when(productRepository.findBy(productId)).thenReturn(Optional.of(product));

        String productName = productService.getProductName(productId);

        assertEquals(expectedProductName, productName);
    }

    @Test
    void getProductName_ShouldReturnDefaultProductNameWhenProductNotFound() {
        final int nonExistentProductId = 99;
        final String defaultProductName = "Missing Product Name";

        when(productRepository.findBy(nonExistentProductId)).thenReturn(Optional.empty());

        String productName = productService.getProductName(nonExistentProductId);

        assertEquals(defaultProductName, productName);
    }
}
