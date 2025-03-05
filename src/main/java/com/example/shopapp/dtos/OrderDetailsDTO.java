package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDetailsDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "order id > 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "product id > 0")
    private Long productId;

    @Min(value = 0, message = "price >= 0")
    private Long price ;

    @JsonProperty("number_of_products")
    @Min(value = 0, message = "số lượng sản phẩm > 0")
    private int numberOfProduct ;

    @JsonProperty("total_money")
    @Min(value = 0, message = "total money >= 0")
    private Float totalMoney ;

    private String color;
}
