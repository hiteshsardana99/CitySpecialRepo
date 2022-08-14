package com.sardana.cityspecial.service.elasticsearch;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public interface ElasticSearchService {

  boolean index(Map<String, Object> document, String indexName, String adId);

  boolean update(Map<String, Object> document, String indexName, String adId);

  Optional<SearchResponse> search(SearchRequest searchRequest);
}
