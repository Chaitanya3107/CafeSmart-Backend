package com.example.CafeSmart.Backend.service;

import com.example.CafeSmart.Backend.model.MenuItem;
import com.example.CafeSmart.Backend.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

    public List<MenuItem> getMenu() {
        return menuRepository.findAll();
    }

    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuRepository.save(menuItem);
    }



    public void deteteMenuItem(Long id) {
        menuRepository.deleteById(id);
    }

    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
       Optional<MenuItem> existingMenuItem = menuRepository.findById(id);
       if(existingMenuItem.isPresent()){
           MenuItem updatedItem = existingMenuItem.get();
           updatedItem.setName(menuItem.getName());
           updatedItem.setDescription(menuItem.getDescription());
           updatedItem.setPrice(menuItem.getPrice());
           return menuRepository.save(updatedItem);
       }
       return null;
    }
}






