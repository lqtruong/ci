package com.pycogroup.training.order.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CheckedStatus {
  private Result status = Result.NOT_VERIFIED;
  private String reason;

  public static CheckedStatus newInstance() {
    return new CheckedStatus();
  }

  boolean isRejected() {
    return status.isFailed() || status.isNotVerified();
  }

  boolean isAccepted() {
    return status.isSuccessful();
  }

  public static enum Result {
    NOT_VERIFIED, SUCCESSFUL, FAILED;

    boolean isNotVerified() {
      return this == NOT_VERIFIED;
    }

    boolean isSuccessful() {
      return this == SUCCESSFUL;
    }

    boolean isFailed() {
      return this == FAILED;
    }

  }

  public boolean isNotVerified() {
    return status.isNotVerified();
  }
}