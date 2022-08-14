package com.sardana.cityspecial.controller;


import com.sardana.cityspecial.model.*;
import com.sardana.cityspecial.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;

import static com.sardana.cityspecial.model.Page.DEFAULT_PAGE_SIZE;


@RestController
@RequestMapping("/api")
public class AdController {

  @Autowired
  AdService adService;

  @PostMapping(value = "/postAd",
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse insertAd(@RequestBody Ad ad) {
    if (adService.indexAd(ad)) {
      return new ApiResponse(201, true, "Successfully added");
    }
    return new ApiResponse(400, false, "Invalid Request");
  }

  @PutMapping(value = "/updateAd",
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse updateAd(@RequestBody Ad ad) {
    if (ad.getId() != null && adService.updateAd(ad)) {
      return new ApiResponse(200, true, "Successfully updated");
    }
    return new ApiResponse(400, false, "Invalid Request");
  }

  @GetMapping(value = "/fetch")
  public ListingResponse fetchAds(
    @RequestParam("tags") String tags,
    @RequestParam("from") int from,
    @RequestParam(value = "size", defaultValue=DEFAULT_PAGE_SIZE) int size) {
    RequestParameter requestParameter = new RequestParameter(new Page(from, size), Arrays.asList(tags.split(",")));
    return adService.fetchAds(requestParameter);
  }


}
