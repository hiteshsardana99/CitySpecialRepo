package com.sardana.cityspecial.service.algorithms;


import com.sardana.cityspecial.model.Ad;
import com.sardana.cityspecial.model.Index;
import com.sardana.cityspecial.model.ListingResponse;
import com.sardana.cityspecial.model.RequestParameter;
import com.sardana.cityspecial.service.elasticsearch.ElasticSearchService;
import com.sardana.cityspecial.util.Mapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Basic implements Algorithm{

  @Autowired
  ElasticSearchService elasticSearchService;

  private static final String VERSION = "1.0.0";

  @Override
  public ListingResponse getSearchRequest(RequestParameter requestParameter) {
    ListingResponse response = new ListingResponse();
    SearchRequest searchRequest = buildSearchRequest(requestParameter);

    Optional<SearchResponse> searchResponse = elasticSearchService.search(searchRequest);

    if (searchResponse.isPresent()) {
      List<Ad> ads = Mapper.parseSearchResponse(searchResponse.get());
      response.setAds(ads);
      response.setVersion(VERSION);
    }
    return response;
  }


  public SearchRequest buildSearchRequest(RequestParameter requestParameter) {
    SearchRequest searchRequest = new SearchRequest(Index.ADS.toString());
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
    searchSourceBuilder.from(requestParameter.getPage().getFrom());
    searchSourceBuilder.size(requestParameter.getPage().getSize());

    searchRequest.source(searchSourceBuilder);
    return searchRequest;
  }

}
