package com.ecommerce.library.service;

import com.ecommerce.library.model.Order;

import java.util.List;

public interface AdminOrderService {
    List<Order> findAll();
    void acceptOrder(Long orderId);
    void rejectOrder(Long orderId);
}
