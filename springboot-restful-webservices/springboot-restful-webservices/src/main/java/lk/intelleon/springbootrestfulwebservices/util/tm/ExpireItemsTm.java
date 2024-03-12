package lk.intelleon.springbootrestfulwebservices.util.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpireItemsTm {
    private Long id;
    private String qty;
    private String itemName;
    private String expireDate;
    private String categoryName;
}
