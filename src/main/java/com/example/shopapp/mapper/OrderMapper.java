package com.example.shopapp.mapper;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.model.Order;
import com.example.shopapp.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDTO orderDTO);

    OrderResponse toOrderResponse (Order order);
    void updateOrder(@MappingTarget Order order, OrderDTO orderDTO);
}
