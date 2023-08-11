package com.example.RecipeBook.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecipesToShow implements Serializable{

    private long id;

    private String title;

    private String dishImage;

    private String description;

    private int cookingTimeMinutes;

    private int portions;

    private String userUsername;

    private List<String> listTags = new ArrayList<>();

    private int countLikes;

    private int countFavourites;

    private boolean likedByUser;

    private boolean favouriteByUser;
}
