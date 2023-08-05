package com.example.RecipeBook.controllers;

import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UsersService usersService;


    @GetMapping("/registration")
    private String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    private String addUser(@RequestParam String username, @RequestParam String password,
                           @RequestParam String name, @RequestParam String email, Model model){
        if ((username != "") && (password != "") && (name != "") && (email != "")){
            Users userFromDb = usersService.findByUsername(username);
            if (userFromDb != null){
                model.addAttribute("errorLogin", true);
                return "registration";
            }
            else {

                usersService.addNewUser(username,password, name, email);
                return "redirect:/login";
            }
        }
        else {
            model.addAttribute("errorMissingText" , true);
            return "registration";
        }
    }
}
