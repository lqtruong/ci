package com.pycogroup.training.order.controller;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pycogroup.training.order.entity.OrderStatus;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest {

  private String id;

  private OrderStatus status = OrderStatus.NEW;
  private double price;
  private String customerId;
  private String accountId;
  private List<String> productIds;

}
