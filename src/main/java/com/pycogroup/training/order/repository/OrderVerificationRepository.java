package com.pycogroup.training.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pycogroup.training.order.entity.OrderVerification;

public interface OrderVerificationRepository extends MongoRepository<OrderVerification, String>, OrderRepositoryCustom {

  Optional<OrderVerification> findByIdAndDeletedFalse(final String id);

  List<OrderVerification> findByIdInAndDeletedFalse(final List<String> ids);

}
