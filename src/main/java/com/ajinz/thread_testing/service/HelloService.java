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
    //    first do ctrl ctrl - gradle build", then do this in terminal
    // we need to do gradle build coz running the green play button wont create jar again..
    //    and to set XSS we need terminal,
    // may be we can config from inettlij, but need to check
    //    java -Xss10m -jar build/libs/loadtesting-demo-0.0.1-SNAPSHOT.jar

    // now the below operation will not throw stackoverflow error because each thread has 10 mb

    // random number 3000-3003 range is working, 15000 not working, throwing stackoverflow error
    Random random = new Random();
    int randomNumber = 3000 + random.nextInt(3003); // Generate random number between 1000 and 10000

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
