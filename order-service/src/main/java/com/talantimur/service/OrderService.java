package com.talantimur.service;

import com.talantimur.client.InventoryClient;
import com.talantimur.dto.OrderRequest;
import com.talantimur.model.Order;
import com.talantimur.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());
        if (isProductInStock) {
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .skuCode(orderRequest.skuCode())
                    .price(orderRequest.price())
                    .quantity(orderRequest.quantity()).
                    build();
            orderRepository.save(order);
        }else {
            throw new RuntimeException("Product with SkuCode"+orderRequest.skuCode()+" is not in stock");
        }



    }
}
