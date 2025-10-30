package com.example.multithreading.javaconcurrency;

import java.util.concurrent.*;

class SumTaskRecursive extends RecursiveTask<Integer> {
    private int start, end;
    public SumTaskRecursive(int start, int end) {
        this.start = start; this.end = end;
    }

    protected Integer compute() {
        if (end - start <= 3) {
            int sum = 0;
            for (int i = start; i <= end; i++) sum += i;
            return sum;
        } else {
            int mid = (start + end) / 2;
            SumTaskRecursive left = new SumTaskRecursive(start, mid);
            SumTaskRecursive right = new SumTaskRecursive(mid + 1, end);
            left.fork();
            int rightResult = right.compute();
            int leftResult = left.join();
            return leftResult + rightResult;
        }
    }
}

public class ForkJoinExample {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        SumTaskRecursive task = new SumTaskRecursive(1, 10);
        int result = pool.invoke(task);
        System.out.println("Total Sum = " + result);
    }
}
