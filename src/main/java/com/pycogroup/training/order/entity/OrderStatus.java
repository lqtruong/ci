package com.pycogroup.training.order.entity;

public enum OrderStatus {

  NEW, PROCESSING, ACCEPTED, DONE, REJECTED;

  public boolean isRejected() {
    return this == REJECTED;
  }

  public boolean isAccepted() {
    return this == ACCEPTED;
  }

}
