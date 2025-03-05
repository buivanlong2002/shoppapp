package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderDTO {
    @Min(value = 1, message = "user id > 0")
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "phone number is required")
    @Size(min = 5, message = "ít nhất 5 ký tự")
    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("total_money")
    @Min(value = 0, message = "money >= 0")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod ;

    @JsonProperty("shipping_address")
    private String shippingAddress ;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate ;


    @JsonProperty("payment_method")
    private String paymentMethod ;

}
