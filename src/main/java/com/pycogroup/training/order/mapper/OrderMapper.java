package com.pycogroup.training.order.mapper;

import org.mapstruct.Mapper;

import com.pycogroup.training.order.controller.OrderRequest;
import com.pycogroup.training.order.controller.OrderResponse;
import com.pycogroup.training.order.controller.OrderVerificationResponse;
import com.pycogroup.training.order.entity.Order;
import com.pycogroup.training.order.entity.OrderVerification;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  OrderResponse toResponse(final Order order);

  Order toOrder(final OrderRequest orderRequest);

  OrderVerificationResponse toVerificationResponse(final OrderVerification orderVerification);

}
