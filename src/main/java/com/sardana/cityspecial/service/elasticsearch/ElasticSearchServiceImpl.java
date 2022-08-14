package com.sardana.cityspecial.service.elasticsearch;

import com.sardana.cityspecial.source.Provider;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.index.VersionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class ElasticSearchServiceImpl implements ElasticSearchService {

  @Autowired
  private Provider<RestHighLevelClient> clientProvider;


  public boolean index(Map<String, Object> document, String indexName, String adId) {

    IndexResponse indexResponse = null;
    RestHighLevelClient restHighLevelClient = clientProvider.get();

    try {
      IndexRequest indexRequest = new IndexRequest(indexName).source(document).id(adId);

      updateIndexElasticConfig(indexRequest);
      indexResponse = restHighLevelClient.index(indexRequest, getRequestOptions());

    } catch (IOException e) {
      System.out.println("Exception occur while indexing document");
    }

    return indexResponse != null && indexResponse.status() != null && isSuccess(indexResponse.status().getStatus());
  }

  @Override
  public boolean update(Map<String, Object> document, String indexName, String adId) {
    UpdateResponse updateResponse = null;
    RestHighLevelClient restHighLevelClient = clientProvider.get();

    try {
      UpdateRequest updateRequest = new UpdateRequest(indexName, adId).doc(document);

      updateElasticConfig(updateRequest);

      updateResponse = restHighLevelClient.update(updateRequest, getRequestOptions());

    } catch (IOException e) {
      System.out.println("Exception occur while updating document");
    }

    return updateResponse != null && updateResponse.status() != null && isSuccess(updateResponse.status().getStatus());
  }

  public Optional<SearchResponse> search(SearchRequest searchRequest) {
    RestHighLevelClient restHighLevelClient = clientProvider.get();

    try {
      SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

      if (isSuccess(searchResponse.status().getStatus())) {
        //parsing
         return Optional.of(searchResponse);
      }
    } catch (IOException e) {
      System.out.println("Exception occur while fetching data from Elastic Search " + Arrays.toString(e.getStackTrace()));
    }
    return Optional.empty();
  }


  public static boolean isSuccess(int code) {
    return 200 <= code && code <= 299;
  }

  private RequestOptions getRequestOptions() {

    RequestOptions.Builder options = RequestOptions.DEFAULT.toBuilder();
//    for (Header header : headers) {
//      Objects.requireNonNull(header, "header cannot be null");
//      options.addHeader(header.getName(), header.getValue());
//    }
    return options.build();

  }


  private void updateIndexElasticConfig(IndexRequest request){
    request.timeout("1s");
    request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
    request.setRefreshPolicy("wait_for");
    request.version(7);
    request.versionType(VersionType.EXTERNAL);
    request.opType(DocWriteRequest.OpType.INDEX);
    request.opType("index");
  }

  private void updateElasticConfig(UpdateRequest request){
    request.timeout("1s");
    request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
    request.setRefreshPolicy("wait_for");
    request.retryOnConflict(3);
  }



}
