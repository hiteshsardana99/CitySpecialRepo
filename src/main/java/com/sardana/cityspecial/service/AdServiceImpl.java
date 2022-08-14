package com.sardana.cityspecial.service;

import com.sardana.cityspecial.model.*;
import com.sardana.cityspecial.service.algorithms.Algorithm;
import com.sardana.cityspecial.service.algorithms.AlgorithmFactory;
import com.sardana.cityspecial.service.elasticsearch.ElasticSearchService;
import com.sardana.cityspecial.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.UUID;

@Component
public class AdServiceImpl implements AdService{

  @Autowired
  ElasticSearchService elasticSearchService;

  @Autowired
  AlgorithmFactory algorithmFactory;

  private static final String LIVE_ALGO = "basic";


  @Override
  public boolean indexAd(Ad ad) {
    Map<String, Object> document = Mapper.getIndexDocument(ad);
    return elasticSearchService.index(document, Index.ADS.toString(), generateAdId(ad));
  }

  @Override
  public boolean updateAd(Ad ad) {
    Map<String, Object> document = Mapper.getIndexDocument(ad);
    return elasticSearchService.update(document, Index.ADS.toString(), ad.getId());
  }

  @Override
  public ListingResponse fetchAds(RequestParameter requestParameter) {
    Algorithm algorithm = algorithmFactory.getAlgo(LIVE_ALGO);
    return algorithm.getSearchRequest(requestParameter);
  }

  private String generateAdId(Ad ad) {
    return (ad.getReportedId() + UUID.randomUUID().toString());
  }

}
