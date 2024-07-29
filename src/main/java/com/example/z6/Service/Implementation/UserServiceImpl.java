package com.example.z6.Service.Implementation;

import com.example.z6.Entities.User;
import com.example.z6.Repositories.UserRepo;
import com.example.z6.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public List<User> getListUsers() {
        return userRepo.findAll();
    }

    @Override
    public User createUser(User user){
        return userRepo.save(user);
    }
}
