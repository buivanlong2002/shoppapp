package com.example.shopapp.service;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.dtos.OrderDetailsDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderDetails;

import java.util.List;

public interface IOrderDetailsService {
    OrderDetails addOrderDetails(OrderDetailsDTO orderDetailsDTO) throws DataNotFoundException;
    OrderDetails updateOrderDetails(Long id ,OrderDetailsDTO orderDetailsDTO) throws DataNotFoundException;
    OrderDetails getOrderDetailsById(long id) throws DataNotFoundException;
    void deleteOrderDetailsById(Long id);
    List<OrderDetails> getAllOrderDetails(Long orderId);
}
