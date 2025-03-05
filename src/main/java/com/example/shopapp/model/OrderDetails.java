package com.example.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name="order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id",nullable=false)
    private Product product;

    private float price;


    @Column(name="number_of_products",nullable=false)
    private int numberOfProducts;

    @Column(name="total_money",nullable=false)
    private Float totalMoney;

    @Column(name="color")
    private String color;
}
