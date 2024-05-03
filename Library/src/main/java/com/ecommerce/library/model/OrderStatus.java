package com.ecommerce.library.model;

public enum OrderStatus {
    ACCEPTED("SHIPPING"),
    REJECTED("REJECTED"),
    PENDING("PENDING"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
