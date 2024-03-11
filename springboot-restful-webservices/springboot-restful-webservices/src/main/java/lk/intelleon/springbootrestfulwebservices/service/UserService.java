package lk.intelleon.springbootrestfulwebservices.service;

import lk.intelleon.springbootrestfulwebservices.dto.LoginDTO;
import lk.intelleon.springbootrestfulwebservices.dto.UserDTO;
import lk.intelleon.springbootrestfulwebservices.response.AuthenticationResponse;

public interface UserService {
    AuthenticationResponse register(UserDTO user);

    AuthenticationResponse authenticate(LoginDTO user);
}
