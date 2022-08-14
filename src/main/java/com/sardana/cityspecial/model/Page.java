package com.sardana.cityspecial.model;

import lombok.Data;

@Data
public class Page {

  public static final String DEFAULT_PAGE_SIZE = "20";

  int from;

  int size;

  public Page(int from, int size) {
    this.from = from;
    this.size = size;
  }

}
