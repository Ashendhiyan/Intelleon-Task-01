package lk.intelleon.springbootrestfulwebservices.service;

import lk.intelleon.springbootrestfulwebservices.dto.LoginDTO;
import lk.intelleon.springbootrestfulwebservices.dto.UserDTO;
import lk.intelleon.springbootrestfulwebservices.response.LoginResponse;

public interface UserService {
    void saveUser(UserDTO userDTO);

    LoginResponse loginUser(LoginDTO loginDTO);
}
