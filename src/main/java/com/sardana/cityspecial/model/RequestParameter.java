package com.sardana.cityspecial.model;

import lombok.Getter;

import java.util.List;

@Getter
public class RequestParameter {

  List<String> tags;

  Page page;

  String adId;

  public RequestParameter(Page page, List<String> tags) {
    this.page = page;
    this.tags = tags;
  }

  public RequestParameter(String adId) {
    this.adId = adId;
  }

}
