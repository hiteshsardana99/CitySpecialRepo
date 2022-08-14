package com.sardana.cityspecial.model;

public enum AdType {

  NEWS,
  FEATURED;

  public static AdType fromString(String value) {
    return AdType.valueOf(value.toUpperCase());
  }

}
