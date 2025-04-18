package com.example.CafeSmart.Backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Links to the Users table
    private User user;

    private double totalPrice;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")  // Store Order Time in "HH:MM DD-MM-YYYY" format
    private LocalDateTime orderTime = LocalDateTime.now();

//  if you do something to CustomerOrder, it will apply to all its orderItems automatically (CascadeType.ALL)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) // (orphanRemoval) If an OrderItem is removed from the orderItems list, it should be deleted from the database too
    private List<OrderItem> orderItems = new ArrayList<>();

}
