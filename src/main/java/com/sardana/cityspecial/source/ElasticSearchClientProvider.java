package com.sardana.cityspecial.source;

import com.sardana.cityspecial.config.ElasticSearchConfiguration;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElasticSearchClientProvider implements Provider<RestHighLevelClient> {

  @Autowired
  private ElasticSearchConfiguration elasticSearchConfiguration;

  private RestHighLevelClient restHighLevelClient;

  private synchronized void init() {
    if (restHighLevelClient == null) {
      this.restHighLevelClient = new RestHighLevelClient(
        RestClient.builder(
          new HttpHost(elasticSearchConfiguration.getHost(),
            elasticSearchConfiguration.getPort(),
            elasticSearchConfiguration.getScheme())));
    }
  }

  @Override
  public RestHighLevelClient get() {
    if (restHighLevelClient == null) {
      init();
    }
    return restHighLevelClient;
  }


  public void stop() throws IOException {
    if (restHighLevelClient != null) {
      restHighLevelClient.close();
    }
  }
}
