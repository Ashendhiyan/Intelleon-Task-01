package lk.intelleon.springbootrestfulwebservices.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationResponse {
    private String token;
    private String message;

}
