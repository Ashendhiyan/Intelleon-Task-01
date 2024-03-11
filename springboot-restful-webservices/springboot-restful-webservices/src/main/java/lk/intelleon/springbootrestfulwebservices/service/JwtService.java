package lk.intelleon.springbootrestfulwebservices.service;

import io.jsonwebtoken.Claims;
import lk.intelleon.springbootrestfulwebservices.dto.LoginDTO;
import lk.intelleon.springbootrestfulwebservices.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {

    String extractUsername(String token);

    boolean isValid(String token, UserDetails user);

    <T> T extractClaim(String token, Function<Claims, T> resolver);

    String generateToken(UserEntity user);
    String generateToken(LoginDTO user);

}
