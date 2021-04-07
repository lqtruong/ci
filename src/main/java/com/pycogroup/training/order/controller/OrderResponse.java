package com.pycogroup.training.order.controller;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pycogroup.training.order.entity.DatePatterns;
import com.pycogroup.training.order.entity.OrderStatus;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {

  private String id;

  private OrderStatus status;
  private double price;
  private String customerId;
  private String accountId;
  private List<String> productIds;

  @JsonFormat(pattern = DatePatterns.DATE_TIME_PATTERN)
  private LocalDateTime created;

  @JsonFormat(pattern = DatePatterns.DATE_TIME_PATTERN)
  private LocalDateTime lastModified;

}
