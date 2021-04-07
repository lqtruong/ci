package com.pycogroup.training.order.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pycogroup.training.order.entity.Order;
import com.pycogroup.training.order.entity.OrderVerification;

public interface OrderService {

  void process(final OrderVerification orderVerification);

  Order create(final Order order);

  Order getById(final String id);

  OrderVerification createVerification(final OrderVerification orderVerification);

  Page<Order> getByAccountId(final String accountId, final Pageable pageable);

  void delete(final String id);

  OrderVerification getVerificationById(final String orderId);

  List<OrderVerification> getVerificationByIds(final List<String> orderIds);

}
