package com.example.RecipeBook.controllers;

import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RegistrationController {

    private final UsersService usersService;

    public RegistrationController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/registration")
    private String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    private String addUser(@RequestParam String login, @RequestParam String password,
                           @RequestParam String name, @RequestParam String email, Model model){
        Users userFromDb = usersService.getUserByLogin(login);
        if (userFromDb != null){
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "registration";
        }
        else {
            usersService.addNewUser(login,password, name, email);
            return "redirect:/login";
        }

    }
}
