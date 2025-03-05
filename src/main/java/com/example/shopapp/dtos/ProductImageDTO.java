package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductImageDTO {
    @Min(value = 1,message = "productId > 0")
   @JsonProperty("product_id")
    private Long productId;

   @Size(min = 5, max = 200,message = "image's name")
    @JsonProperty("image_url")
    private String imageUrl;
}
