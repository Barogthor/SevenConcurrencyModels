package org.example;

public class Puzzle {
    static boolean answerReady = false;
    static int answer = 0;
    static Thread t1 = new Thread() {
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            answer = 42;
            answerReady = true;

        }
    };
    static Thread t2 = new Thread(() -> {
        if (answerReady)
            System.out.println("The meaning of life is: " + answer);
        else
            System.out.println("I don't know the answer");
    });

    public static void main(String[] args) throws InterruptedException {
        t1.start(); t2.start();
        t1.join(); t2.join();
    }
}
