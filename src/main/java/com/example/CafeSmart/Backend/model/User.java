package com.example.CafeSmart.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = true)  // Allow null password for OAuth users
    private String password;
    @Column(nullable = false)
    private String role;
    // Set default role before persisting to the database
    @PrePersist
    public void setDefaultRole() {
        if (this.role == null) {
            this.role = "USERS";
        }
    }
}
