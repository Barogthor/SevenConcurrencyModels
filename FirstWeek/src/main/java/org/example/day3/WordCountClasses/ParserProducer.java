/***
 * Excerpted from "Seven Concurrency Models in Seven Weeks",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/pb7con for more book information.
***/
package org.example.day3.WordCountClasses;
import java.util.concurrent.BlockingQueue;
public class ParserProducer implements Runnable {
  private final String filename;
  private BlockingQueue<Page> queue;

  public ParserProducer(BlockingQueue<Page> queue, String filename) {
    this.queue = queue;
    this.filename = filename;
  }
  
  public void run() {
    try {
      Iterable<Page> pages = new Pages(100000, filename);
      for (Page page: pages) {
        queue.put(page);
        System.out.println("Put page to count, "+queue.size()+" page in queue");
      }
    } catch (Exception e) { e.printStackTrace(); }
  }
}
