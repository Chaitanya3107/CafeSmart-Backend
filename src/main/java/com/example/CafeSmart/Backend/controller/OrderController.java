package com.example.CafeSmart.Backend.controller;

import com.example.CafeSmart.Backend.model.CustomerOrder;
import com.example.CafeSmart.Backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;  

    @PostMapping("/place")
    public CustomerOrder placeOrder(@RequestBody CustomerOrder customerOrder){
        return orderService.placeOrder(customerOrder);
    }

    @GetMapping("/myOrders")
    public List<CustomerOrder> getUserOrders() {
        return orderService.getUserOrders();
    }



}
