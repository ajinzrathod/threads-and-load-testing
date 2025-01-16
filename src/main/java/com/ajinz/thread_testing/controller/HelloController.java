package com.ajinz.thread_testing.controller;

import com.ajinz.thread_testing.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {
  private static final Logger log = LoggerFactory.getLogger(HelloController.class);
  private static long maxJVMAndOSOverhead = 0;
  private static long maxMetaSpace = 0;
  private static long maxHeapMemory = 0;
  private AtomicLong counter = new AtomicLong();

  @Autowired HelloService helloService = new HelloService();

  @GetMapping({"hello", "hello/"})
  public String hello() throws InterruptedException {
    Thread.sleep(3000);
    counter.incrementAndGet();
    return "<h1>Hello</h1>" + Thread.currentThread();
  }

  @GetMapping({"count", "count/"})
  public String count() {
    return "<h1>" + counter.get() + "</h1>";
  }

  @GetMapping({"long", "long/"})
  public String sqrt() {
    return helloService.sqrt();
  }

  @GetMapping({"r", "r/"})
  public String deepRecursive() {
    return helloService.deepRecursiveCall();
  }

  @GetMapping({"max", "max/"})
  public String printMax() {

    return "Restart the build to reset the max value.<br>"
        + "maxMetaSpace: "
        + maxMetaSpace
        + "<br>"
        + "maxJVMAndOSOverhead: "
        + maxJVMAndOSOverhead
        + "<br>"
        + "maxHeapMemory: "
        + maxHeapMemory;
  }

  public void getMemoryDetails() {
    // Get Heap Memory
    long heapMemoryUsed = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
    long heapMemoryMax = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax();

    // Get Thread Stack Memory (stack size of the current thread)
    long threadStackSize = Thread.currentThread().getStackTrace().length * 1024L; // Estimate in KB

    // Get Metaspace Memory
    long metaspaceUsed = 0;
    long metaspaceMax = 0;
    List<MemoryPoolMXBean> memoryPools = ManagementFactory.getMemoryPoolMXBeans();
    for (MemoryPoolMXBean pool : memoryPools) {
      if (pool.getName().contains("Metaspace")) {
        MemoryUsage usage = pool.getUsage();
        metaspaceUsed = usage.getUsed();
        metaspaceMax = usage.getMax();
      }
    }

    // Get Total JVM Memory (including heap and metaspace)
    long totalMemory = Runtime.getRuntime().totalMemory();

    // Get OS Overhead (system managed memory)
    long osOverhead = totalMemory - (heapMemoryUsed + metaspaceUsed);

    // Format response
    long metaspace = metaspaceUsed / (1024 * 1024);
    long overhead = osOverhead / (1024 * 1024);
    long heapMemory = heapMemoryUsed / (1024 * 1024);
    log.info(
        String.format(
            """

                        Heap Memory: %d MB / %d MB
                        Thread Stack Memory: %d KB
                        Metaspace: %d MB / %d MB
                        JVM and OS Overhead: %d MB
                        """,
            heapMemory,
            heapMemoryMax / (1024 * 1024),
            threadStackSize / 1024,
            metaspace,
            metaspaceMax / (1024 * 1024),
            overhead));
    maxMetaSpace = Math.max(maxMetaSpace, metaspace);
    maxJVMAndOSOverhead = Math.max(maxJVMAndOSOverhead, overhead);
    maxHeapMemory = Math.max(maxHeapMemory, heapMemory);
  }
}
