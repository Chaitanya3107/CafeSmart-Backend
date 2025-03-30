package com.example.CafeSmart.Backend.controller;

import com.example.CafeSmart.Backend.model.MenuItem;
import com.example.CafeSmart.Backend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    @GetMapping("/all")
    public List<MenuItem> getAllMenuItems(){
        return menuService.getMenu();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')") // only admins can add menu
    public MenuItem addMenuItems(@RequestBody MenuItem menuItem){
        return menuService.addMenuItem(menuItem);
    }

    @PostMapping("/delete{id}")
    @PreAuthorize("hasRole('ADMIN')") // only admins can add menu
    public String deleteMenuItem(@PathVariable  Long id){
        menuService.deteteMenuItem(id);
        return "Menu item deleted successfully.";
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        return menuService.updateMenuItem(id, menuItem);
    }

}















