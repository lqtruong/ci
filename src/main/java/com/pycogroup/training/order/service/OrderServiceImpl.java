package com.pycogroup.training.order.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pycogroup.training.order.entity.Order;
import com.pycogroup.training.order.entity.OrderVerification;
import com.pycogroup.training.order.repository.OrderRepository;
import com.pycogroup.training.order.repository.OrderVerificationRepository;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository repository;

  @Autowired
  private OrderVerificationRepository verificationRepository;

  @Override
  @Transactional
  public void process(final OrderVerification orderVerification) {
    log.info("Order Verification: {}", orderVerification);
    final Order foundOrder = getById(orderVerification.getId());
    if (Objects.isNull(foundOrder)) {
      log.info("Order {} not found", orderVerification.getId());
      return;
    }
    final OrderVerification verification = getVerificationById(foundOrder.getId());
    OrderVerification newVerification = verification;
    if (Objects.isNull(verification)) {
      newVerification = OrderVerification.from(foundOrder);
    }
    if (!orderVerification.getBalanceCheck().isNotVerified()) {
      newVerification.setBalanceCheck(orderVerification.getBalanceCheck());
    }
    if (!orderVerification.getProductCheck().isNotVerified()) {
      newVerification.setProductCheck(orderVerification.getProductCheck());
    }
    final OrderVerification savedOrderVerification = createVerification(newVerification);
    log.info("New Order Verification status updated: {}", savedOrderVerification);
    foundOrder.setStatus(savedOrderVerification.getStatus());
    repository.save(foundOrder);
    log.info("Order status updated: {}", foundOrder);
  }

  @Override
  @Transactional
  public Order create(final Order order) {
    return repository.save(order);
  }

  @Override
  public Order getById(final String id) {
    return repository.findByIdAndDeletedFalse(id).orElse(null);
  }

  @Override
  @Transactional
  public OrderVerification createVerification(final OrderVerification orderVerification) {
    return verificationRepository.save(orderVerification);
  }

  @Override
  public Page<Order> getByAccountId(final String accountId, final Pageable pageable) {
    return repository.findByAccountId(accountId, pageable);
  }

  @Override
  @Transactional
  public void delete(final String id) {
    final Order foundOrder = getById(id);
    if (Objects.isNull(foundOrder)) {
      log.info("Order {} not found", id);
      return;
    }
    foundOrder.setDeleted(true);
    repository.save(foundOrder);

    final OrderVerification verification = getVerificationById(id);
    if (Objects.isNull(verification)) {
      log.info("OrderVerification {} not found", id);
      return;
    }
    verification.setDeleted(true);
    verificationRepository.save(verification);
  }

  @Override
  public OrderVerification getVerificationById(final String orderId) {
    return verificationRepository.findByIdAndDeletedFalse(orderId).orElse(null);
  }

  @Override
  public List<OrderVerification> getVerificationByIds(final List<String> orderIds) {
    final List<Order> orders = repository.findByIdInAndDeletedFalse(orderIds);
    if (CollectionUtils.isEmpty(orders)) {
      return Collections.emptyList();
    }
    // check all orders belong to the same customer
    final boolean ordersBelongToDifferentCustomers =
        orders.stream().map(order -> order.getCustomerId()).distinct().count() > 1;
    if (ordersBelongToDifferentCustomers) {
      throw new InvalidOrdersException("Orders should belong to unique customer");
    }
    final List<OrderVerification> verifiedOrders = verificationRepository.findByIdInAndDeletedFalse(orderIds);
    // if not all order accepted
    final boolean atLeastOneOrderNotAccepted =
        verifiedOrders.stream().anyMatch(verifiedOrder -> !verifiedOrder.getStatus().isAccepted());
    if (atLeastOneOrderNotAccepted) {
      throw new InvalidOrdersException("All orders must be accepted");
    }
    return verifiedOrders;
  }

}
