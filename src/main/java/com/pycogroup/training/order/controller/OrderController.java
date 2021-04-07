package com.pycogroup.training.order.controller;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pycogroup.training.order.entity.Order;
import com.pycogroup.training.order.entity.OrderVerification;
import com.pycogroup.training.order.mapper.OrderMapper;
import com.pycogroup.training.order.service.OrderService;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderMapper orderMapper;

  @PostMapping
  public ResponseEntity<OrderResponse> create(final @RequestBody OrderRequest order) {
    final Order savedOrder = orderService.create(orderMapper.toOrder(order));
    log.info("Order saved: {}", savedOrder);
    final OrderVerification verification = orderService.createVerification(OrderVerification.from(savedOrder));
    log.info("OrderVerification saved: {}", verification);
    return new ResponseEntity(orderMapper.toResponse(savedOrder), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> findById(final @PathVariable("id") String id) {
    return ResponseEntity.ok(orderMapper.toResponse(orderService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<Page<OrderResponse>> getAllOrders(
      final @RequestParam("accountId") String accountId, final @PageableDefault Pageable pageable) {
    final Page<Order> orders = orderService.getByAccountId(accountId, pageable);
    log.info("Orders found: {}", orders);
    return ResponseEntity.ok(orders.map(order -> orderMapper.toResponse(order)));
  }

  @DeleteMapping("/{id}")
  public void delete(final @PathVariable("id") String id) {
    orderService.delete(id);
  }

  @PostMapping("/verify")
  public ResponseEntity<List<OrderVerificationResponse>> verify(
      final @RequestBody OrderVerificationRequest orderVerificationRequest) {
    final List<OrderVerification> orders = orderService.getVerificationByIds(orderVerificationRequest.getOrderIds());
    return ResponseEntity.ok(orders.stream().map(
        order -> orderMapper.toVerificationResponse(order)).collect(Collectors.toList()));
  }

}
