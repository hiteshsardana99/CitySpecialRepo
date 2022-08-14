package com.sardana.cityspecial.model;

import lombok.Data;

import java.util.List;

@Data
public class Ad {

  private String id;

  private String timestamp;

  private String title;

  private String description;

  private List<String> imageUrls;

  private List<String> tags;

  private AdStatus status;

  private String reportedId;

  private String reporterName;

  private AdType adType;

}
