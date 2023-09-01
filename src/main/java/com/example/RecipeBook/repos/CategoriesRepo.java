package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepo extends JpaRepository<Categories, Long> {

    Categories findById(long id);
}
