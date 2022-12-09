/***
 * Excerpted from "Seven Concurrency Models in Seven Weeks",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/pb7con for more book information.
***/
package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Philosopher extends Thread {
  private final int maxSecondsEatingTime;
  private final int maxSecondsThinkingTime;
  private Chopstick left, right;
  private Random random;
  private int thinkCount;

  public Philosopher(Chopstick left, Chopstick right, int maxSecondsEatingTime, int maxSecondsThinkingTime) {
    this.left = left; this.right = right;
    this.maxSecondsEatingTime = maxSecondsEatingTime * 1000;
    this.maxSecondsThinkingTime = maxSecondsThinkingTime * 1000;
    random = new Random();
  }

  public void run() {
    try {
      while(true) {
        ++thinkCount;
        if (thinkCount % 10 == 0)
          System.out.println("Philosopher " + this + " has thought " + thinkCount + " times");
        System.out.println(this + " is thinking.");
        Thread.sleep(random.nextInt(this.maxSecondsThinkingTime));     // Think for a while
        System.out.println(this + " try to takes chopstick left " + left.getId());
        synchronized(left) {                    // Grab left chopstick
          System.out.println(this + " try to takes chopstick right " + right.getId());
          synchronized(right) {                 // Grab right chopstick
            System.out.println(this + " is eating.");
            Thread.sleep(random.nextInt(this.maxSecondsEatingTime)); // Eat for a while
          }
        }
      }
    } catch(InterruptedException e) {}
  }

  public static void main(String[] args) throws InterruptedException {
    final int number = 30;
    List<Chopstick> chopsticks = new ArrayList<>();
    List<Thread> philosophers = new ArrayList<>();
    for(int i = 0 ; i < number ; i++) {
      chopsticks.add(new Chopstick(i));
    }
    for(int i = 0 ; i < number ; i++) {
      int leftId = i;
      int rightId = (i+1)%number;
      Philosopher e = new Philosopher(chopsticks.get(leftId), chopsticks.get(rightId), 10, 5);
      philosophers.add(e);
      e.start();
    }

  }
}
