package com.example.CafeSmart.Backend.repository;

import com.example.CafeSmart.Backend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem,Long> {

}
