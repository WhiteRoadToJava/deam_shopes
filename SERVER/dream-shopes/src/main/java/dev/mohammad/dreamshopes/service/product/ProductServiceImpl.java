package dev.mohammad.dreamshopes.service.product;

import dev.mohammad.dreamshopes.exception.ProductNotFoundException;
import dev.mohammad.dreamshopes.model.Category;
import dev.mohammad.dreamshopes.model.Product;

import dev.mohammad.dreamshopes.repository.CategoryRepository;
import dev.mohammad.dreamshopes.repository.ProductRepository;
import dev.mohammad.dreamshopes.request.AddProductRequest;
import dev.mohammad.dreamshopes.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository priductRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // ckecke if the category os found in the DB

        // uf yes,  set it as the new produvt category


        // if no, the save if as a new category

        // the set as the new product

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(
                        () -> {
                            Category newCategory = new Category(request.getCategory().getName());
                            return categoryRepository.save(newCategory);
                        });
        request.setCategory(category);
        return priductRepository.save(createProduct(request, category));

    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getDescription(),
                request.getInventory(),
                category
        );
    }

    @Override
    public Product getProductById(long id) {
        return priductRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, long priductId) {
        return priductRepository.findById(priductId)
                .map(exsistingproduct -> updateExistingProduct(exsistingproduct, request))
                .map(priductRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));


    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setInventory(request.getInventory());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public void deleteProduct(long id) {
        priductRepository.findById(id).ifPresentOrElse(priductRepository::delete,
                () -> {
                    throw new ProductNotFoundException("Product not found");
                });
    }

    @Override
    public List<Product> getAllProducts() {
        return priductRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return priductRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getAllProductsByBrand(String brand) {
        return priductRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getAllProductsByCategoryAndBrand(String category, String brand) {
        return priductRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getAllProductByName(String name) {
        return priductRepository.findByName(name);
    }

    @Override
    public List<Product> getAllProductByCategoryAndName(String category, String name) {
        return List.of();
    }

    @Override
    public List<Product> getAllProductByBrandAndName(String brand, String name) {
        return priductRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countAllProductsByBrandAndName(String brand, String name) {
        return priductRepository.countByBrandAndName(brand, name);
    }
}
