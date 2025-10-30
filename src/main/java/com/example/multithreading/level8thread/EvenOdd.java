package com.example.multithreading.level8thread;

class Printer {
    private boolean evenTurn = false;

    public synchronized void printEven(int num) throws InterruptedException {
        while (!evenTurn) wait();
        System.out.println("Even: " + num);
        evenTurn = false;
        notifyAll();
    }

    public synchronized void printOdd(int num) throws InterruptedException {
        while (evenTurn) wait();
        System.out.println("Odd: " + num);
        evenTurn = true;
        notifyAll();
    }
}

public class EvenOdd {
    public static void main(String[] args) {
        Printer p = new Printer();

        new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                try { p.printOdd(i); } catch (InterruptedException e) {}
            }
        }).start();

        new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                try { p.printEven(i); } catch (InterruptedException e) {}
            }
        }).start();
    }
}

