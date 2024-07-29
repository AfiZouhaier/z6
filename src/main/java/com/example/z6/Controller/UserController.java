package com.example.z6.Controller;


import com.example.z6.Entities.User;
import com.example.z6.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getListUsers();
    }

    @PostMapping("/add")
    private User  addUser(@RequestBody User reqBody){
        return userService.createUser(reqBody);
    }
}
