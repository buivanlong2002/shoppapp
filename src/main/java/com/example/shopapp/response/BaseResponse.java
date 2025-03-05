package com.example.shopapp.response;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class BaseResponse {
    @Column(name="create_at")
    private LocalDateTime createAt;

    @Column(name="update_at")
    private LocalDateTime updateAt;
//    @PrePersist
//    protected void onCreate() {
//        createAt = LocalDateTime.now();
//        updateAt = LocalDateTime.now();
//    }
//    @PreUpdate
//    protected void onUpdate() {
//        updateAt = LocalDateTime.now();
//    }
}
