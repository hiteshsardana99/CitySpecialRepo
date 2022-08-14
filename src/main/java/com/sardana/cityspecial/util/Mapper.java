package com.sardana.cityspecial.util;

import com.sardana.cityspecial.model.Ad;
import com.sardana.cityspecial.model.AdStatus;
import com.sardana.cityspecial.model.AdType;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.sardana.cityspecial.model.ElasticSearchField.*;

public class Mapper {

  private void Mapper() {}

  public static List<Ad> parseSearchResponse(SearchResponse searchResponse) {

    if (searchResponse != null && searchResponse.getHits() != null) {
      List<Ad> ads = new ArrayList<>();

      for (SearchHit searchHit : searchResponse.getHits()) {
        ads.add(mapSearchHitToAd(searchHit));
      }
      return ads;
    }
    return Collections.emptyList();
  }

  private static Ad mapSearchHitToAd(SearchHit searchHit) {
    Ad ad = new Ad();

    ad.setId(searchHit.getId());

    for (Map.Entry<String, Object> document : searchHit.getSourceAsMap().entrySet()) {

      switch (document.getKey()) {
        case TITLE : ad.setTitle(document.getValue().toString());
          break;
        case DESCRIPTION : ad.setDescription(document.getValue().toString());
          break;
        case REPORTER_ID : ad.setReportedId(document.getValue().toString());
          break;
        case REPORTER_NAME : ad.setReporterName(document.getValue().toString());
          break;
        case STATUS : ad.setStatus(AdStatus.fromString(document.getValue().toString()));
          break;
        case AD_TYPE : ad.setAdType(AdType.fromString(document.getValue().toString()));
          break;
        case UDPDATED_AT : ad.setTimestamp(ad.getTimestamp());
          break;
        case IMAGE_URLS : ad.setImageUrls(parseValues(document.getValue()));
          break;
        case TAGS : ad.setTags(parseValues(document.getValue()));
          break;
      }
    }

    return ad;
  }

  public static List<String> parseValues(Object arraysObject) {
    if (arraysObject instanceof ArrayList && !((ArrayList) arraysObject).isEmpty()) {
      ArrayList<Object> arrays = (ArrayList) arraysObject;
      return arrays.stream().map(v -> Objects.toString(v, null)).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  public static Map<String, Object> getIndexDocument(Ad ad) {
    Map<String, Object> document = new HashMap<>();

    Optional.ofNullable(ad.getId()).map( id -> document.put(ID, id));

    Optional.ofNullable(ad.getTitle()).map( title -> document.put(TITLE, title));

    Optional.ofNullable(ad.getDescription()).map( description -> document.put(DESCRIPTION, description));

    Optional.ofNullable(ad.getImageUrls()).map( urls -> document.put(IMAGE_URLS, urls));

    Optional.ofNullable(ad.getTags()).map( tags -> document.put(TAGS, tags));

    Optional.ofNullable(ad.getStatus()).map(status -> document.put(STATUS, status));

    Optional.ofNullable(ad.getReportedId()).map(reporterId -> document.put(REPORTER_ID, reporterId));

    Optional.ofNullable(ad.getAdType()).map( type -> document.put(AD_TYPE, type));

    document.put(UDPDATED_AT, LocalDateTime.now());

    return document;
  }

}
