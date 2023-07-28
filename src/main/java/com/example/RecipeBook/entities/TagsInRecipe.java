package com.example.RecipeBook.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "tags_in_recipe")
public class TagsInRecipe implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "recipe_id")
    private long recipeId;

    @Column(name = "tag_id")
    private long tagId;

    @ManyToOne
    private Recipes recipes;

    @ManyToOne
    private Tags tags;
}
