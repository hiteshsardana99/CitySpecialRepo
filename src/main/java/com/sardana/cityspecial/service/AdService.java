package com.sardana.cityspecial.service;

import com.sardana.cityspecial.model.Ad;
import com.sardana.cityspecial.model.ListingResponse;
import com.sardana.cityspecial.model.RequestParameter;
import org.springframework.stereotype.Service;


@Service
public interface AdService {

  boolean indexAd(Ad ad);

  boolean updateAd(Ad ad);

  ListingResponse fetchAds(RequestParameter requestParameter);

}
