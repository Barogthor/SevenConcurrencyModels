/***
 * Excerpted from "Seven Concurrency Models in Seven Weeks",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/pb7con for more book information.
***/
package org.example.day3.WordCountClasses;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.Map;

public class CounterWorker implements Runnable {
  private BlockingQueue<Page> queue;
  private Map<String, Integer> sharedCounts;
  private Map<String, Integer> counts;
  
  public CounterWorker(BlockingQueue<Page> queue,
                       Map<String, Integer> counts) {
    this.queue = queue;
    this.sharedCounts = counts;
    this.counts = new HashMap<>();
  }

  public void run() {
    try {
      while(true) {
        Page page = queue.take();
        if (page.isPoisonPill())
          break;

        Iterable<String> words = new Words(page.getText());
        for (String word: words)
          countWord(word);
      }
    } catch (Exception e) { e.printStackTrace(); }
    mergeCount();
  }

  private void countWord(String word) {
    Integer currentCount = counts.putIfAbsent(word,1);
    if (currentCount != null)
      counts.replace(word, currentCount + 1);
  }

  private void mergeCount() {
    for (Map.Entry<String, Integer> entry : this.counts.entrySet()) {
      String word = entry.getKey();
      Integer localCount = entry.getValue();
      Integer shareCount = this.counts.putIfAbsent(word, localCount);
      if(shareCount != null)
        this.sharedCounts.replace(word, shareCount + localCount);
    }

  }
}
