package lk.intelleon.springbootrestfulwebservices.service.impl;

import lk.intelleon.springbootrestfulwebservices.dto.LoginDTO;
import lk.intelleon.springbootrestfulwebservices.dto.UserDTO;
import lk.intelleon.springbootrestfulwebservices.entity.UserEntity;
import lk.intelleon.springbootrestfulwebservices.repo.UserRepository;
import lk.intelleon.springbootrestfulwebservices.response.LoginResponse;
import lk.intelleon.springbootrestfulwebservices.service.UserService;
import lk.intelleon.springbootrestfulwebservices.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Convertor convertor;

    @Override
    public void saveUser(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity(
                userDTO.getId(),
                userDTO.getUserName(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword())
        );
        repository.save(userEntity);
    }

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        String msg = "";
        UserEntity userEntity = repository.findByUserName(loginDTO.getUserName());
        if (userEntity != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = userEntity.getPassword();
            boolean isPasswordMatch = passwordEncoder.matches(password, encodedPassword);
            if (isPasswordMatch) {
                Optional<UserEntity> user = repository.findOneByUserNameAndPassword(loginDTO.getUserName(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginResponse("Login success", true);
                } else {
                    return new LoginResponse("Login failed", false);
                }
            } else {
                return new LoginResponse("Password not match", false);
            }
        } else {
            return new LoginResponse("Username not exist", false);
        }
    }
}
