package com.sardana.cityspecial.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {

  int statusCode;

  boolean success;

  String error;

}
