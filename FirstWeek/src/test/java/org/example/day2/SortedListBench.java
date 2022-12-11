package org.example.day2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SortedListBench {
    private static final int NUM_THREADS = 8;
    private static final int MAX_ITERATION = (int) Math.pow(2,16);
    private static final int MAX_RAND = 100_000;
    private static final int CONCURRENT_ITERATION = MAX_ITERATION / NUM_THREADS;

    private class HandOverLockListRunnable implements Runnable {
        private HandOverLockSortedList list;
        public HandOverLockListRunnable(HandOverLockSortedList list) {
            this.list = list;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Random random = new Random();
            for (int j = 0; j < CONCURRENT_ITERATION; j++) {
                list.insert(random.nextInt(MAX_RAND));
            }
        }
    }
    private class SingleLockListRunnable implements Runnable {
        private SingleLockSortedList list;
        public SingleLockListRunnable(SingleLockSortedList list) {
            this.list = list;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Random random = new Random();
            for (int j = 0; j < CONCURRENT_ITERATION; j++) {
                list.insert(random.nextInt(MAX_RAND));
            }
        }
    }
    @Test
    public void testHandOverLockList() {
        HandOverLockSortedList sortedList = new HandOverLockSortedList();
        Random random = new Random();
        for( int i = 0 ; i < MAX_ITERATION; i++)
            sortedList.insert(random.nextInt(MAX_RAND));
        assertEquals(MAX_ITERATION, sortedList.size());
        assertTrue(sortedList.isSorted());
    }

    @Test
    public void testConcurrentlyHandOverLockList() throws InterruptedException {
        HandOverLockSortedList sortedList = new HandOverLockSortedList();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < NUM_THREADS; i++) {
            threads.add(new Thread(new HandOverLockListRunnable(sortedList)));
        }
        threads.forEach(Thread::start);
        for (int i = 0; i < NUM_THREADS; i++) {
            threads.get(NUM_THREADS - i - 1).join();
        }
        assertEquals(MAX_ITERATION, sortedList.size());
        assertTrue(sortedList.isSorted());
    }

    @Test
    public void testSingleLockList() {
        SingleLockSortedList sortedList = new SingleLockSortedList();
        Random random = new Random();
        for( int i = 0 ; i < MAX_ITERATION; i++)
            sortedList.insert(random.nextInt(MAX_RAND));
        assertEquals(MAX_ITERATION, sortedList.size());
        assertTrue(sortedList.isSorted());
    }

    @Test
    public void testConcurrentlySingleLockList() throws InterruptedException {
        SingleLockSortedList sortedList = new SingleLockSortedList();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < NUM_THREADS; i++) {
            threads.add(new Thread(new SingleLockListRunnable(sortedList)));
        }
        threads.forEach(Thread::start);
        for (int i = 0; i < NUM_THREADS; i++) {
            threads.get(NUM_THREADS - i - 1).join();
        }
        assertEquals(MAX_ITERATION, sortedList.size());
        assertTrue(sortedList.isSorted());
    }
}