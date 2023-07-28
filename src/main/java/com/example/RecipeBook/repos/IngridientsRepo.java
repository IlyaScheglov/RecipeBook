package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Ingridients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngridientsRepo extends JpaRepository<Ingridients,Long> {
}
