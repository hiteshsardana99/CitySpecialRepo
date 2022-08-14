package com.sardana.cityspecial.model;


import lombok.Data;

import java.util.List;

@Data
public class ListingResponse {

  private String version;

  private String status;

  private List<Ad> ads;

  private Metadata metadata;

}
