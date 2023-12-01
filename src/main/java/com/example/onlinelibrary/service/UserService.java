package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.*;

public interface UserService {
    UserDTO login(UserDTO user);
    UserDTO register(UserDTO user);
    UserDTO update(UserDTO user, Long id);
    UserDTO get(Long id);
    UserDTO delete(Long id);
}
