package com.sardana.cityspecial.model;

public enum AdStatus {

  ACTIVE,
  PENDING,
  BLOCKED_BY_ADMIN,
  BLOCKED_BY_REPORTER,
  DELETED;

  public static AdStatus fromString(String value) {
    return AdStatus.valueOf(value.toUpperCase());
  }

}
