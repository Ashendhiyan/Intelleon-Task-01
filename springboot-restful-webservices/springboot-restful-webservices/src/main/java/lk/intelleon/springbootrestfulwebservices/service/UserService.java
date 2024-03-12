package lk.intelleon.springbootrestfulwebservices.service;

import lk.intelleon.springbootrestfulwebservices.dto.LoginDTO;
import lk.intelleon.springbootrestfulwebservices.dto.UserDTO;
import lk.intelleon.springbootrestfulwebservices.response.AuthenticationResponse;

import java.util.List;

public interface UserService {
    AuthenticationResponse register(UserDTO user);

    AuthenticationResponse authenticate(LoginDTO user);

    List<UserDTO> getAllUsers();
}
