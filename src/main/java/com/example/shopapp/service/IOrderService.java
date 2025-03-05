package com.example.shopapp.service;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Order;
import com.example.shopapp.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    Order addOrder(OrderDTO orderDTO) throws Exception;

    Order updateOrder(Long id ,OrderDTO orderDTO) throws DataNotFoundException;


    Order getOrderById(long userId);
    void deleteOrderById(Long orderId);
    List<Order> getAllOrderUserId(Long userId);
}
