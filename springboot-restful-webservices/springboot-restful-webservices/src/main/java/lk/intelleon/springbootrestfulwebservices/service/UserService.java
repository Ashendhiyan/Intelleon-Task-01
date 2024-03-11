package lk.intelleon.springbootrestfulwebservices.service;

import lk.intelleon.springbootrestfulwebservices.dto.LoginDTO;
import lk.intelleon.springbootrestfulwebservices.dto.UserDTO;
import lk.intelleon.springbootrestfulwebservices.response.AuthenticationResponse;

public interface UserService {
//    void saveUser(UserDTO userDTO);
//
//    LoginResponse loginUser(LoginDTO loginDTO);
    AuthenticationResponse register(UserDTO user);

    AuthenticationResponse authenticate(UserDTO user);
}
