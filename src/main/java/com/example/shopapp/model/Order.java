package com.example.shopapp.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="full_name",  length = 100)
    private String fullName;

    @Column(name="email",  length = 100)
    private String email;

    @Column(name="phone_number",nullable = false,  length = 100)
    private String phoneNumber;

    @Column(name="address",  length = 100)
    private String address;

    @Column(name="note",  length = 100)
    private String note;

    @Column(name="order_date",  length = 100)
    private Date orderDate;


    @Column(name="status")
    private String status;

    @Column(name="total_money")
    private Float totalMoney;


    @Column(name="shipping_method")
    private String shippingMethod;


    @Column(name="shipping_address")
    private String shippingAddress;


    @Column(name="shipping_date")
    private LocalDate shippingDate;


    @Column(name="tracking_number")
    private String trackingNumber;

    @Column(name="payment_method")
    private String paymentMethod;

    @Column(name="active")
    private boolean active;


}
