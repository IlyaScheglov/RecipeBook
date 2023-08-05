package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepo extends JpaRepository<Tags, Long> {

    Tags findByTitle(String title);
}
