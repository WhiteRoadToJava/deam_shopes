package dev.mohammad.dreamshopes.service.categoty;

import dev.mohammad.dreamshopes.exception.AlreadyExistsException;
import dev.mohammad.dreamshopes.exception.CategoryNotFoundException;
import dev.mohammad.dreamshopes.model.Category;
import dev.mohammad.dreamshopes.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatgegoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private Category category;

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " is already exist"));
    }

    @Override
    public Category updateCategory(Category category, long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory ->{
                oldCategory.setName(category.getName());
                return categoryRepository.save(oldCategory);
                }).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategoryById(long id) {
     categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, ()-> {
        throw  new CategoryNotFoundException("Category not found");
        });

    }
}
