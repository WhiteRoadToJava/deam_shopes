package dev.mohammad.dreamshopes.service.categoty;

import dev.mohammad.dreamshopes.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();

    Category addCategory(Category category);
    Category updateCategory(Category category,  long id);
    void deleteCategoryById(long id);
}
