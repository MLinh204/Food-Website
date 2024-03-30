package com.project.library.service;

import com.project.library.model.Order;
import com.project.library.model.ShoppingCart;

import java.util.List;


public interface OrderService {
    Order save(ShoppingCart shoppingCart);

    List<Order> findAll(String username);

    List<Order> findALlOrders();

    Order acceptOrder(Long id);

    void cancelOrder(Long id);
}
