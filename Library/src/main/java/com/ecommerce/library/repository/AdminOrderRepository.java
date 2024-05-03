package com.ecommerce.library.repository;

import com.ecommerce.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminOrderRepository extends JpaRepository<Order, Long> {
    // Add any custom queries or methods here if needed
}
