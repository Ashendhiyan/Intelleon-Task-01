package lk.intelleon.springbootrestfulwebservices.projection;

import java.time.LocalDate;


public interface InventoryProjection {
    Long getId();
    String getQTY();
    String getItemName();
    LocalDate getExpireDate();
    String getCategoryName();
}
