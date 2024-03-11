package lk.intelleon.springbootrestfulwebservices.service.impl;

import lk.intelleon.springbootrestfulwebservices.dto.LoginDTO;
import lk.intelleon.springbootrestfulwebservices.dto.UserDTO;
import lk.intelleon.springbootrestfulwebservices.entity.UserEntity;
import lk.intelleon.springbootrestfulwebservices.repo.UserRepository;
import lk.intelleon.springbootrestfulwebservices.service.JwtService;
import lk.intelleon.springbootrestfulwebservices.service.UserService;
import lk.intelleon.springbootrestfulwebservices.response.AuthenticationResponse;
import lk.intelleon.springbootrestfulwebservices.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Convertor convertor;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;



    public AuthenticationResponse register(UserDTO request) {

        // check if user already exist. if exist than authenticate the user
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist");
        }

        UserEntity user = new UserEntity();
//        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(request.getStatus());

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt, "User registration was successful");

    }

    public AuthenticationResponse authenticate(UserDTO user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        String jwt = jwtService.generateToken(repository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));

        return new AuthenticationResponse(jwt, "User login was successful");

    }
}
