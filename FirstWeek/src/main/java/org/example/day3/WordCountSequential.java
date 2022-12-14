/***
 * Excerpted from "Seven Concurrency Models in Seven Weeks",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/pb7con for more book information.
***/
package org.example.day3;

import org.example.day3.WordCountClasses.Page;
import org.example.day3.WordCountClasses.Pages;
import org.example.day3.WordCountClasses.Words;

import java.util.HashMap;

public class WordCountSequential {
  private static final HashMap<String, Integer> counts = 
    new HashMap<String, Integer>();

  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();
    Iterable<Page> pages = new Pages(100000, "enwiki-20221201-pages-articles-multistream1.xml");
    for(Page page: pages) {
      Iterable<String> words = new Words(page.getText());
      for (String word: words)
        countWord(word);
    }
    long end = System.currentTimeMillis();
    System.out.println("Elapsed time: " + (end - start) + "ms");

    // for (Map.Entry<String, Integer> e: counts.entrySet()) {
    //   System.out.println(e);
    // }
  }

  private static void countWord(String word) {
    Integer currentCount = counts.get(word);
    if (currentCount == null)
      counts.put(word, 1);
    else
      counts.put(word, currentCount + 1);
  }
}
