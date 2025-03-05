package com.example.shopapp.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApiResponse<T> {
    public String message;
    public String code;
    public T data;

}
