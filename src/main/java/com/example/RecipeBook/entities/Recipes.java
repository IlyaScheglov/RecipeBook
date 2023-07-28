package com.example.RecipeBook.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "recipes")
public class Recipes implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "dish_image")
    private String dishImage;

    @Column(name = "description")
    private String description;

    @Column(name = "category_type")
    private long categoryType;

    @Column(name = "cooking_time_minutes")
    private int cookingTimeMinutes;

    @Column(name = "portions")
    private int portions;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne
    private Users users;

    @OneToMany(mappedBy = "recipes")
    private List<Favourites> favourites = new ArrayList<>();

    @OneToMany(mappedBy = "recipes")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "recipes")
    private List<Steps> steps = new ArrayList<>();

    @OneToMany(mappedBy = "recipes")
    private List<Ingridients> ingridients = new ArrayList<>();

    @ManyToOne
    private Categories categories;

    @OneToMany(mappedBy = "recipes")
    private List<TagsInRecipe> tagsInRecipes = new ArrayList<>();
}
