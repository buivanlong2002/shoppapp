package com.example.shopapp.response;

import com.example.shopapp.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductResponse extends BaseResponse{

    private String name;
    private float price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private Long category;
}
