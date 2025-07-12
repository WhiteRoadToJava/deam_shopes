package dev.mohammad.dreamshopes.service.product;

import dev.mohammad.dreamshopes.dto.ImageDto;
import dev.mohammad.dreamshopes.dto.ProductDto;
import dev.mohammad.dreamshopes.exception.CategoryNotFoundException;
import dev.mohammad.dreamshopes.exception.ProductNotFoundException;
import dev.mohammad.dreamshopes.model.Category;
import dev.mohammad.dreamshopes.model.Image;
import dev.mohammad.dreamshopes.model.Product;

import dev.mohammad.dreamshopes.repository.CategoryRepository;
import dev.mohammad.dreamshopes.repository.ImageRepository;
import dev.mohammad.dreamshopes.repository.ProductRepository;
import dev.mohammad.dreamshopes.request.AddProductRequest;
import dev.mohammad.dreamshopes.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;


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
        return productRepository.save(createProduct(request, category));

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
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, long productId) {
        return productRepository.findById(productId)
                .map(exsistingproduct -> updateExistingProduct(exsistingproduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));


    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        // Apply null checks if fields in request are optional for update
        if (request.getName() != null) {
            existingProduct.setName(request.getName());
        }
        if (request.getBrand() != null) {
            existingProduct.setBrand(request.getBrand());
        }
        // Assuming price, description, inventory are always present or intended to be updated even with nulls if request allows
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setInventory(request.getInventory());

        // CRITICAL FIX: Add null checks for request.getCategory()
        if (request.getCategory() != null && request.getCategory().getName() != null) {
            String categoryName = request.getCategory().getName();
            Category category = categoryRepository.findByName(categoryName);

            if (category == null) {
                // IMPROVEMENT: Use a more semantically correct exception
                throw new CategoryNotFoundException("Category not found with name: " + categoryName);
            }
            existingProduct.setCategory(category);
        }
        // else if request.getCategory() is null, the existing category remains unchanged.
        // If you want to explicitly allow setting the category to null, you'd add logic here.

        return existingProduct;
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> {
                    throw new ProductNotFoundException("Product not found");
                });
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getAllProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getAllProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getAllProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getAllProductByCategoryAndName(String category, String name) {
        return List.of();
    }

    @Override
    public List<Product> getAllProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countAllProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }


    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::converToDto).toList();
    }


@Override
public ProductDto converToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imagesDto = images.stream().map(image -> modelMapper.map(image, ImageDto.class))
                .collect(Collectors
                        .toList());

        return productDto;
    }

}
