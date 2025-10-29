package com.example.multithreading.synchronization;

class Printer {
    public void printDocs(String docName) {
        synchronized (this) { // lock only inside block
            for (int i = 1; i <= 3; i++) {
                System.out.println(Thread.currentThread().getName() + " printing " + docName + " - page " + i);
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        }
    }
}
public class SyncBlockDemo {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread t1 = new Thread(() -> printer.printDocs("FileA.pdf"), "User-1");
        Thread t2 = new Thread(() -> printer.printDocs("FileB.pdf"), "User-2");

        t1.start();
        t2.start();
    }
}

