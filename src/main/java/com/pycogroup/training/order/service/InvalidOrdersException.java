package com.pycogroup.training.order.service;

public class InvalidOrdersException extends IllegalArgumentException {

  public InvalidOrdersException(final String message) {
    super(message);
  }
}
