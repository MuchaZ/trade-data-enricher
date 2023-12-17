package pl.arciemowicz.tradedataenricher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.test.util.ReflectionTestUtils;
import pl.arciemowicz.tradedataenricher.dto.Product;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryCsvFileImplTest {

    private ProductRepositoryCsvFileImpl productRepository;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        productRepository = new ProductRepositoryCsvFileImpl();
        Path tempFile = Files.createFile(tempDir.resolve("test_products.csv"));
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("ProductId,ProductName\n");
            writer.write("1,Product 1\n");
            writer.write("2,Product 2\n");
        }
        ReflectionTestUtils.setField(productRepository, "path", tempFile.toString());
    }

    @Test
    void findBy_ShouldReturnProductIfExists() {
        Optional<Product> product = productRepository.findBy(1);

        assertTrue(product.isPresent());
        assertEquals("Product 1", product.get().getProductName());
    }

    @Test
    void findBy_ShouldReturnEmptyIfNotExists() {
        Optional<Product> product = productRepository.findBy(99);

        assertFalse(product.isPresent());
    }

    @Test
    void findBy_ShouldHandleEmptyFile() throws IOException {
        Path emptyFile = createEmptyCsvFile();
        ReflectionTestUtils.setField(productRepository, "path", emptyFile.toString());

        Optional<Product> product = productRepository.findBy(1);

        assertFalse(product.isPresent());
    }

    @Test
    void findBy_ShouldHandleFileWithOnlyHeader() throws IOException {
        Path fileWithOnlyHeader = createCsvFileWithOnlyHeader();
        ReflectionTestUtils.setField(productRepository, "path", fileWithOnlyHeader.toString());

        Optional<Product> product = productRepository.findBy(1);

        assertFalse(product.isPresent());
    }

    private Path createEmptyCsvFile() throws IOException {
        return Files.createFile(tempDir.resolve("empty.csv"));
    }

    private Path createCsvFileWithOnlyHeader() throws IOException {
        Path file = Files.createFile(tempDir.resolve("header_only.csv"));
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("ProductId,ProductName\n");
        }
        return file;
    }
}
