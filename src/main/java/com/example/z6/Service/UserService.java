package com.example.z6.Service;

import com.example.z6.Entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<User> getListUsers();
    public User createUser(User user);
}

