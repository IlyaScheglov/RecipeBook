package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Steps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepsRepo extends JpaRepository<Steps, Long> {
}
