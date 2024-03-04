package lk.intelleon.springbootrestfulwebservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDTO {
    private Long id;
    private String code;
    private String name;
    private String status;
}
