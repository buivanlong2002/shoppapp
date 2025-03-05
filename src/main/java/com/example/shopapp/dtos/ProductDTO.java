package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductDTO {
    @NotBlank(message = "title is required")
    @Size(min = 3 , max= 200 , message = "title must be between 3 and 200 characters")
    private String name ;


    @Min(value = 0 , message = "price >= 0")
    @Max(value = 1000000 , message =  "price <= 10,000,000")
    private float price;


    @Min(value = 0 , message = "thumbnail >= 0")
    @Max(value = 100 , message =  "thumbnail <= 100")
    private String thumbnail;


    private String description;


    @JsonProperty("category_id")
//    @NotNull(message = "Category ID is required")
    private Long categoryId;


}
