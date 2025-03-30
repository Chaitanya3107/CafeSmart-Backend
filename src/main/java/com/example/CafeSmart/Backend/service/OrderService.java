package com.example.CafeSmart.Backend.service;

import com.example.CafeSmart.Backend.model.CustomerOrder;
import com.example.CafeSmart.Backend.model.OrderItem;
import com.example.CafeSmart.Backend.repository.MenuRepository;
import com.example.CafeSmart.Backend.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MenuRepository menuRepository;

    @Transactional
    public CustomerOrder placeOrder(CustomerOrder customerOrder) {
        double totalPrice = 0.0;
        for (OrderItem item : customerOrder.getOrderItems()) {
            // Fetch the actual MenuItem from the database
            item.setMenuItem(menuRepository.findById(item.getMenuItem().getId()).orElseThrow(
                    () -> new RuntimeException("Menu item not found")
            ));
            // Calculate price based on menu item price * quantity
            totalPrice += item.getMenuItem().getPrice() * item.getQuantity();
            // Set the reference back to the order
            item.setOrder(customerOrder);
        }
        // Set calculated total price
        customerOrder.setTotalPrice(totalPrice);
        return orderRepository.save(customerOrder);
    }

    public Optional<CustomerOrder> getOrder(long id) {
        return orderRepository.findById(id);
    }
}








