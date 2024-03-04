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
    private Long categoryId; // Assuming this is the ID of the category
    private String categoryName; // Assuming you want to transfer the category name as well
    private Long unitId; // Assuming this is the ID of the unit
    private String unitName; // Assuming you want to transfer the unit name as well
    private String status;
}
