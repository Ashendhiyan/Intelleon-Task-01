package lk.intelleon.springbootrestfulwebservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private Long id;
    private Long itemId;
    private LocalDate receivedDate;
    private int receivedQty;
    private String approvalStatus;
    private String status;
}
