package pl.arciemowicz.tradedataenricher.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.arciemowicz.tradedataenricher.dto.Product;

@Repository
public class ProductRepositoryCsvFileImpl implements ProductRepository {

    @Value("${products.file.path}")
    private String path;
    private Map<Integer, Product> products;

    public void loadProductMap() {
        this.products = new HashMap<>();
        try {
            populateProductsFromPath(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateProductsFromPath(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            skipFirstLine(br);
            String line;
            while ((line = br.readLine()) != null) {
                Optional<Product> product = parseProductFromLine(line);
                product.ifPresent(p -> products.put(p.getProductId(), p));
            }
        }
    }

    private void skipFirstLine(BufferedReader br) throws IOException {
        br.readLine();
    }

    private Optional<Product> parseProductFromLine(String line) {
        String[] values = line.split(",");
        if (isNumeric(values[0])) {
            int productId = Integer.parseInt(values[0]);
            String productName = values[1];
            return Optional.of(new Product(productId, productName));
        }
        return Optional.empty();
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public Optional<Product> findBy(int productId) {
        if (products == null) {
            loadProductMap();
        }
        return Optional.ofNullable(products.get(productId));
    }
}
