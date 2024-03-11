package lk.intelleon.springbootrestfulwebservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String userName;
    private String password;
//    private String status;
}
