package com.example.CafeSmart.Backend.repository;

import com.example.CafeSmart.Backend.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
}
