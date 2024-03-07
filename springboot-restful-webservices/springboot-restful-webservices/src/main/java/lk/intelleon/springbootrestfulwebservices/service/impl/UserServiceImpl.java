package lk.intelleon.springbootrestfulwebservices.service.impl;

import lk.intelleon.springbootrestfulwebservices.dto.UserDTO;
import lk.intelleon.springbootrestfulwebservices.repo.UserRepository;
import lk.intelleon.springbootrestfulwebservices.service.UserService;
import lk.intelleon.springbootrestfulwebservices.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    Convertor convertor;
    @Override
    public void saveUser(UserDTO userDTO) {
        repository.save(convertor.UserDtoToUserEntity(userDTO));
    }
}
