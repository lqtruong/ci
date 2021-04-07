package com.pycogroup.training.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.pycogroup.training.order.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String>, OrderRepositoryCustom {

  Optional<Order> findByIdAndDeletedFalse(final String id);

  Page<Order> findById(final String id, Pageable pageable);

  Page<Order> findByAccountId(final String accountId, Pageable pageable);

  List<Order> findByIdInAndDeletedFalse(final List<String> orderIds);
}
