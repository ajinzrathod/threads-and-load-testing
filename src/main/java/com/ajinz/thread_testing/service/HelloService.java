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

  public String deepRecursiveCall() {
    Random random = new Random();
    int randomNumber = 2000 + random.nextInt(1001); // Generate a random number between 2000 and 3000

    // Perform a deep recursive call with the generated random depth
    deepRecursiveCall(randomNumber, 0);

    return "Recursion Completed!";
  }

  private int deepRecursiveCall(int maxDepth, int currentDepth) {
    if (currentDepth >= maxDepth) {
      return 1;
    }

    Random random = new Random();
    int randomValue = 1000 + random.nextInt(9001); // Generate random number

    // Simulate some processing with the random value
    int result = randomValue * 2; // Just a dummy operation to keep the stack busy

    // Recursive call, increment depth
    return 1 + deepRecursiveCall(maxDepth, currentDepth + 1);
  }
}
