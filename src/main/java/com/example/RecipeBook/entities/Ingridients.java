package com.example.RecipeBook.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "ingridients")
public class Ingridients implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "recipe_id")
    private long recipeId;

    @Column(name = "title")
    private String title;

    @Column(name = "list_of_ingridients")
    private String listOfIngridients;

    @ManyToOne
    private Recipes recipes;
}
