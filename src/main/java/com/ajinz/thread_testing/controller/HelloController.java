package com.ajinz.thread_testing.controller;

import com.ajinz.thread_testing.service.HelloService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HelloController {

  @Autowired HelloService helloService = new HelloService();

  @GetMapping({"ping", "ping/", "/"})
  public String health() {
    return "Pong";
  }

  @GetMapping({"hello", "hello/"})
  public String hello(
      HttpServletRequest request,
      @RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor)
      throws InterruptedException {
    Thread.sleep(3000);
    String clientIp = xForwardedFor != null ? xForwardedFor : request.getRemoteAddr();
    return "<h1>Hello " + clientIp + "</h1>";
  }
}
