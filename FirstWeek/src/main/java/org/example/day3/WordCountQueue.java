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
import java.util.concurrent.ArrayBlockingQueue;

public class WordCountQueue {

  public static void main(String[] args) throws Exception {
    ArrayBlockingQueue<Page> queue = new ArrayBlockingQueue<Page>(100);
    HashMap<String, Integer> counts = new HashMap<String, Integer>();

    Thread counter = new Thread(new CounterWorker(queue, counts));
    Thread parser = new Thread(new ParserProducer(queue, "enwiki-20221201-pages-articles-multistream1.xml"));
    long start = System.currentTimeMillis();
	
    counter.start();
    parser.start();
    parser.join();
    queue.put(new PagePoison());
    counter.join();
    long end = System.currentTimeMillis();
    System.out.println("Elapsed time: " + (end - start) + "ms");

    // for (Map.Entry<String, Integer> e: counts.entrySet()) {
    //   System.out.println(e);
    // }
  }
}
