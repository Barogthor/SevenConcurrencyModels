/***
 * Excerpted from "Seven Concurrency Models in Seven Weeks",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/pb7con for more book information.
***/
package org.example.day3;

import org.example.day3.WordCountClasses.CounterWorker;
import org.example.day3.WordCountClasses.Page;
import org.example.day3.WordCountClasses.PagePoison;
import org.example.day3.WordCountClasses.ParserProducer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class WordCountQueueConcurrent {

  public static void main(String[] args) throws Exception {
    ArrayBlockingQueue<Page> queue = new ArrayBlockingQueue<Page>(100);
    Map<String, Integer> counts = new ConcurrentHashMap<>();


    Thread parser = new Thread(new ParserProducer(queue, "enwiki-20221201-pages-articles-multistream1.xml"));
//    int threadPoolSize = Runtime.getRuntime().availableProcessors() * 2;
    int threadPoolSize = 6;
    ExecutorService executor = Executors.newCachedThreadPool();
    for (int i = 0; i < threadPoolSize; i++) {
      executor.execute(new CounterWorker(queue, counts));
    }
    long start = System.currentTimeMillis();
	
    parser.start();
    parser.join();
    for (int i = 0; i < threadPoolSize; i++) {
      queue.put(new PagePoison());
    }
    executor.shutdown();
    executor.awaitTermination(10L, TimeUnit.MINUTES);
    long end = System.currentTimeMillis();
    System.out.println("Elapsed time: " + (end - start) + "ms");

    // for (Map.Entry<String, Integer> e: counts.entrySet()) {
    //   System.out.println(e);
    // }
  }
}
