package dev.mohammad.dreamshopes.service.product;

import dev.mohammad.dreamshopes.model.Product;
import dev.mohammad.dreamshopes.request.AddProductRequest;
import dev.mohammad.dreamshopes.request.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest product);

    Product getProductById(long id);

    Product updateProduct(ProductUpdateRequest request, long priductId);

    void deleteProduct(long id);

    List<Product> getAllProducts();

    List<Product> getAllProductsByCategory(String category);

    List<Product> getAllProductsByBrand(String brand);

    List<Product> getAllProductsByCategoryAndBrand(String category, String brand);

    List<Product> getAllProductByName(String name);
    List<Product> getAllProductByCategoryAndName(String category, String name);
    List<Product> getAllProductByBrandAndName(String brand, String name);

    Long countAllProductsByBrandAndName(String brand, String name);

}

