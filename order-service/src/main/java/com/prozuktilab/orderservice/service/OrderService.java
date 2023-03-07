package com.prozuktilab.orderservice.service;

import com.prozuktilab.orderservice.dto.OrderRequest;

public interface OrderService {
    public void placeOrder(OrderRequest orderRequest);
}
