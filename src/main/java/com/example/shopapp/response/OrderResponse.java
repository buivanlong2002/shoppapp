package com.example.shopapp.response;

import com.example.shopapp.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse extends BaseResponse {
    private Long id;
    @JoinColumn(name="user_id")
    private User user;
    @Column(name="full_name")
    private String fullName;
    private String email;
    @Column(name="phone_number")
    private String phoneNumber;

    private String address;


    private String note;

    @Column(name="order_date")
    private LocalDateTime orderDate;



    private String status;

    @Column(name="total_money")
    private Float totalMoney;


    @Column(name="shipping_method")
    private String shippingMethod;


    @Column(name="shipping_address")
    private String shippingAddress;


    @Column(name="shipping_date")
    private Date shippingDate;


    @Column(name="tracking_number")
    private String trackingNumber;

    @Column(name="payment_method")
    private String paymentMethod;


    private boolean active;


}

