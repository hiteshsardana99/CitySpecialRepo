package com.sardana.cityspecial.service.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmFactory {

  @Autowired
  private ApplicationContext context;

  public Algorithm getAlgo(String algoName){
    return context.getBean(Basic.class);
  }

}
