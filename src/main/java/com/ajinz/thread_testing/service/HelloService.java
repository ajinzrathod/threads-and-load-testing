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
    // Step 1: Perform Gradle Build
    // Use `Ctrl + Ctrl` in IntelliJ to open the command window, then execute:
    // gradle build
    // Note: Running the green play button in IntelliJ won't regenerate the JAR file.
    // Building via Gradle ensures that any changes are reflected in the JAR file.

    // Step 2: Run the Application with Custom Stack Size
    // Use the terminal to set the stack size (Xss) and run the JAR:
    // java -Xss10m -jar build/libs/thread-testing-0.0.1-SNAPSHOT.jar
    // Explanation: The `-Xss10m` flag sets the stack size for each thread to 10 MB.
    // This configuration helps prevent stack overflow errors for deeply recursive calls.

    // Step 3: Test Recursive Calls with Random Depth
    // With the increased stack size, the application will handle deeper recursive calls.
    // Example: Random depth generation between 2000 and 3000 works fine,
    // but a depth of 20000 causes a stack overflow error.
    Random random = new Random();
    int randomNumber = 20000 + random.nextInt(1001); // Generate a random number between 2000 and 3000

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
