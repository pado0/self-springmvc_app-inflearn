package springmvc.application.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// @Data // getter, setter, requiredArgConstructor 다 적용. 위험해서 쓰지 않기
@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity; // null 못들어가도록 Integer로 선언

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
