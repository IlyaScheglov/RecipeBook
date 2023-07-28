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
@Table(name = "users")
public class Users implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "users")
    private List<Recipes> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<Favourites> favourites = new ArrayList<>();

}
