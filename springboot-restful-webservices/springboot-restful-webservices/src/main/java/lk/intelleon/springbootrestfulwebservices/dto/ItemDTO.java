package lk.intelleon.springbootrestfulwebservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private Long id;
    private String code;
    private String name;
    private Long categoryId; // ID of the category
    private Long unitId; // ID of the unit
    private String status;
}
