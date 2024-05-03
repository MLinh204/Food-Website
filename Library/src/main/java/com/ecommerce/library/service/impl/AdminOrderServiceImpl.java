package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderStatus;
import com.ecommerce.library.repository.AdminOrderRepository;
import com.ecommerce.library.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    private AdminOrderRepository adminOrderRepository;

    @Override
    public List<Order> findAll() {
        return adminOrderRepository.findAll();
    }

    @Override
    public void acceptOrder(Long orderId) {
        Order order = adminOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(OrderStatus.ACCEPTED.getStatus());
        adminOrderRepository.save(order);
    }

    @Override
    public void rejectOrder(Long orderId) {
        Order order = adminOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(OrderStatus.REJECTED.getStatus());
        adminOrderRepository.save(order);
    }
}
