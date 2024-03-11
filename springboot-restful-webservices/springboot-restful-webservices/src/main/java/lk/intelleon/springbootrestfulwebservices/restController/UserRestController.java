package lk.intelleon.springbootrestfulwebservices.restController;

import lk.intelleon.springbootrestfulwebservices.dto.LoginDTO;
import lk.intelleon.springbootrestfulwebservices.dto.UserDTO;
import lk.intelleon.springbootrestfulwebservices.service.UserService;
import lk.intelleon.springbootrestfulwebservices.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserRestController {
    @Autowired
    UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> saveUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.register(userDTO));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserDTO loginDTO) {
         AuthenticationResponse response = userService.authenticate(loginDTO);
        return ResponseEntity.ok(response);
    }
}
