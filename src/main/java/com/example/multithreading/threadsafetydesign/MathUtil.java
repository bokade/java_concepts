package com.example.multithreading.threadsafetydesign;

class MathUtil {
    public int square(int x) {
        return x * x; // uses only local variable
    }
}
