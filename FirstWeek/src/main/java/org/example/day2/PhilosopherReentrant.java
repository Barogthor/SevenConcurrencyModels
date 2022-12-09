/***
 * Excerpted from "Seven Concurrency Models in Seven Weeks",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/pb7con for more book information.
***/
package org.example.day2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class PhilosopherReentrant extends Thread {
  private ReentrantLock leftChopstick, rightChopstick;
  private Random random;
  private int thinkCount;

  public PhilosopherReentrant(ReentrantLock leftChopstick, ReentrantLock rightChopstick) {
    this.leftChopstick = leftChopstick; this.rightChopstick = rightChopstick;
    random = new Random();
  }

  public void run() {
    try {
      while(true) {
        ++thinkCount;
        if (thinkCount % 10 == 0)
          System.out.println("Philosopher " + this + " has thought " + thinkCount + " times");
        Thread.sleep(random.nextInt(1000)); // Think for a while
        leftChopstick.lock();
        try {
          if (rightChopstick.tryLock(1000, TimeUnit.MILLISECONDS)) {
            // Got the right chopstick
            try {
              Thread.sleep(random.nextInt(1000)); // Eat for a while
            } finally { rightChopstick.unlock(); }
          } else {
            // Didn't get the right chopstick - give up and go back to thinking
            System.out.println("Philosopher " + this + " timed out");
          }
        } finally { leftChopstick.unlock(); }
      }
    } catch(InterruptedException e) {}
  }

  public static void main(String[] args) throws InterruptedException {
    final int number = 5;
    List<ReentrantLock> chopsticks = new ArrayList<>();
    List<Thread> philosophers = new ArrayList<>();
    for(int i = 0 ; i < number ; i++) {
      chopsticks.add(new ReentrantLock());
    }
    for(int i = 0 ; i < number ; i++) {
      int leftId = i;
      int rightId = (i+1)%number;
      PhilosopherReentrant e = new PhilosopherReentrant(chopsticks.get(leftId), chopsticks.get(rightId));
      philosophers.add(e);
      e.start();
    }

  }
}
