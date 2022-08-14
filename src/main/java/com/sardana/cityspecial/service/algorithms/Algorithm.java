package com.sardana.cityspecial.service.algorithms;


import com.sardana.cityspecial.model.ListingResponse;
import com.sardana.cityspecial.model.RequestParameter;
import org.springframework.stereotype.Service;

@Service
public interface Algorithm {

  ListingResponse getSearchRequest(RequestParameter requestParameter);

}
