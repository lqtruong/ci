package com.pycogroup.training.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document("orders")
@Getter
@Setter
@ToString
public class Order {

  @Id
  @Field(targetType = FieldType.OBJECT_ID)
  private String id;

  private OrderStatus status;
  private double price;
  private String customerId;
  private String accountId;
  private List<String> productIds;
  private boolean deleted = false;

  @CreatedDate
  @JsonFormat(pattern = DatePatterns.DATE_TIME_PATTERN)
  private LocalDateTime created;

  @LastModifiedDate
  @JsonFormat(pattern = DatePatterns.DATE_TIME_PATTERN)
  private LocalDateTime lastModified;
}
