package com.example.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name="tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="token", length=255, nullable=false)
    private String token ;


    @Column(name="token_type", length=55, nullable=false)
    private String tokenType ;

    @Column(name="expiration_date")
    private LocalDateTime expirationDate; /// thời gian token

    @Column(name="revoked_date")
    private boolean revokedDate;

    private boolean expired;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
