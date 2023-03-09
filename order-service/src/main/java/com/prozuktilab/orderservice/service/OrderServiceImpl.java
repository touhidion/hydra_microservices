package com.prozuktilab.orderservice.service;

import com.prozuktilab.orderservice.dto.InventoryResponse;
import com.prozuktilab.orderservice.dto.OrderLineItemDto;
import com.prozuktilab.orderservice.dto.OrderRequest;
import com.prozuktilab.orderservice.model.Order;
import com.prozuktilab.orderservice.model.OrderLineItem;
import com.prozuktilab.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemList(orderRequest.getOrderLineItemDtoList()
                        .stream().map(this::dtoToEntity).toList())
                .build();
        List<String> skuCodes = order.getOrderLineItemList().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();

        //place order if inventory is in stock
        InventoryResponse [] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8083/api/inventory"
                ,uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product not in stock, please try again later.");
        }
    }

    private OrderLineItem dtoToEntity(OrderLineItemDto orderLineItemDto) {
        return OrderLineItem.builder()
                .skuCode(orderLineItemDto.getSkuCode())
                .price(orderLineItemDto.getPrice())
                .quantity(orderLineItemDto.getQuantity())
                .build();
    }
}
