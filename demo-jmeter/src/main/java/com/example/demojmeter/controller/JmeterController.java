package com.example.demojmeter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1")
public class JmeterController {
  
  private static int counter = 0;

  @GetMapping(value = "/jmeter")
  public String count(){
    System.out.println(++counter);
    return "current counter value:" + counter;
  }
}
