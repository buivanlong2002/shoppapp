package com.example.shopapp.response;

import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderDetails;
import com.example.shopapp.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDetailsResponse {

    private Long id;


    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    private float price;


    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("color")
    private String color;

    public static OrderDetailsResponse fromOrderDetailsResponse(OrderDetails orderDetails) {
        return OrderDetailsResponse.builder()
                .id(orderDetails.getId())
                .orderId(orderDetails.getOrder().getId())
                .productId(orderDetails.getProduct().getId())
                .price(orderDetails.getPrice())
                .numberOfProducts(orderDetails.getNumberOfProducts())
                .color(orderDetails.getColor())
                .build();

    }
}

