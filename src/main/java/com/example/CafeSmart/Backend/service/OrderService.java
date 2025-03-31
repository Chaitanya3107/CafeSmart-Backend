package com.example.CafeSmart.Backend.service;

import com.example.CafeSmart.Backend.model.CustomerOrder;
import com.example.CafeSmart.Backend.model.OrderItem;
import com.example.CafeSmart.Backend.model.User;
import com.example.CafeSmart.Backend.repository.MenuRepository;
import com.example.CafeSmart.Backend.repository.OrderRepository;
import com.example.CafeSmart.Backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    UserRepository userRepository;


    @Transactional
    public CustomerOrder placeOrder(CustomerOrder customerOrder) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        customerOrder.setUser(user); // Set authenticated user
        customerOrder.setOrderTime(LocalDateTime.now()); // Set current date and time

        double totalPrice = 0.0;
        for (OrderItem item : customerOrder.getOrderItems()) {
            item.setMenuItem(menuRepository.findById(item.getMenuItem().getId()).orElseThrow(
                    () -> new RuntimeException("Menu item not found")
            ));
            totalPrice += item.getMenuItem().getPrice() * item.getQuantity();
            item.setOrder(customerOrder);
        }

        customerOrder.setTotalPrice(totalPrice);
        return orderRepository.save(customerOrder);
    }

    public Optional<CustomerOrder> getOrder(long id) {
        return orderRepository.findById(id);
    }

    public List<CustomerOrder> getUserOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return orderRepository.findByUser(user);
    }
}








