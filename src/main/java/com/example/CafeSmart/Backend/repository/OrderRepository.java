package com.example.CafeSmart.Backend.repository;

import com.example.CafeSmart.Backend.model.CustomerOrder;
import com.example.CafeSmart.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
    List<CustomerOrder> findByUser(User user);
}
