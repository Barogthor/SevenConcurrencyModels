/***
 * Excerpted from "Seven Concurrency Models in Seven Weeks",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/pb7con for more book information.
***/
package org.example.day2;

import java.util.concurrent.locks.ReentrantLock;

class SingleLockSortedList {


  ReentrantLock lock = new ReentrantLock();
  private class Node {
    int value;
    Node prev;
    Node next;

    Node() {}

    Node(int value, Node prev, Node next) {
      this.value = value; this.prev = prev; this.next = next;
    }
  }

  private final Node head;
  private final Node tail;

  public SingleLockSortedList() {
    head = new Node(); tail = new Node();
    head.next = tail; tail.prev = head;
  }

  public void insert(int value) {
    Node current = head;
    this.lock.lock();
    try {
      Node next = current.next;
      while (true) {
          if (next == tail || next.value < value) {
            Node node = new Node(value, current, next); 
            next.prev = node;
            current.next = node;
            return; 
          }
        current = next;
        next = current.next;
      }
    } finally { this.lock.unlock(); }
  }

  public int size() {
    Node current = tail;
    int count = 0;
	
    while (current.prev != head) {
      this.lock.lock();
      try {
        ++count;
        current = current.prev;
      } finally { this.lock.unlock(); }
    }
    return count;
  }

  public boolean isSorted() {
    Node current = head;
    while (current.next.next != tail) {
      current = current.next;
      if (current.value < current.next.value)
        return false;
    }
    return true;
  }
}
