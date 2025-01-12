package com.ajinz.thread_testing.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class HelloService {
  public String sqrt() {
    long start = System.nanoTime();
    double result = 0;
    for (long i = 0; i < 1_000_000_000_0L; i++) { // Large loop for significant work
      result += Math.sqrt(i); // Some CPU-bound work
    }
    long end = System.nanoTime();
    return "Result: " + result + "<br> Time Taken: " + (end - start) / 1_000_000_000.0 + "s";
  }
}
