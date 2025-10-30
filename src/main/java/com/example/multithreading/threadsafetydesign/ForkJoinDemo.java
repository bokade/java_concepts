package com.example.multithreading.threadsafetydesign;

import java.util.concurrent.*;

class SumTask extends RecursiveTask<Long> {
    private final long[] arr;
    private final int start, end;

    SumTask(long[] arr, int start, int end) {
        this.arr = arr; this.start = start; this.end = end;
    }

    protected Long compute() {
        if (end - start <= 3) {
            long sum = 0;
            for (int i = start; i < end; i++) sum += arr[i];
            return sum;
        }
        int mid = (start + end) / 2;
        SumTask left = new SumTask(arr, start, mid);
        SumTask right = new SumTask(arr, mid, end);
        left.fork();
        return right.compute() + left.join();
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        long[] arr = {1,2,3,4,5,6,7,8,9,10};
        SumTask task = new SumTask(arr, 0, arr.length);
        System.out.println("Sum: " + pool.invoke(task));
    }
}

