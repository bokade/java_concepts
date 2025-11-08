package com.example.integerMethods;

public class IntegerClassDemo {
    public static void main(String[] args) {
        System.out.println("===== 1️⃣ Conversion Methods =====");

        // parseInt(String s)
        int num1 = Integer.parseInt("123");
        System.out.println("parseInt(\"123\") → " + num1);

        // valueOf(String s)
        Integer numObj1 = Integer.valueOf("456");
        System.out.println("valueOf(\"456\") → " + numObj1);

        // valueOf(int i)
        Integer numObj2 = Integer.valueOf(789);
        System.out.println("valueOf(789) → " + numObj2);

        // toString()
        System.out.println("toString() → " + numObj2.toString());

        // toBinaryString, toHexString, toOctalString
        int val = 25;
        System.out.println("toBinaryString(25) → " + Integer.toBinaryString(val));
        System.out.println("toHexString(25) → " + Integer.toHexString(val));
        System.out.println("toOctalString(25) → " + Integer.toOctalString(val));

        // byteValue, shortValue, longValue, floatValue, doubleValue
        Integer wrapper = 100;
        System.out.println("byteValue() → " + wrapper.byteValue());
        System.out.println("shortValue() → " + wrapper.shortValue());
        System.out.println("longValue() → " + wrapper.longValue());
        System.out.println("floatValue() → " + wrapper.floatValue());
        System.out.println("doubleValue() → " + wrapper.doubleValue());

        System.out.println("\n===== 2️⃣ Comparison Methods =====");

        int x = 10, y = 20;
        System.out.println("compare(10, 20) → " + Integer.compare(x, y));
        System.out.println("compare(20, 10) → " + Integer.compare(20, 10));
        System.out.println("compare(10, 10) → " + Integer.compare(10, 10));

        Integer obj1 = 100;
        Integer obj2 = 200;
        System.out.println("compareTo() → " + obj1.compareTo(obj2));
        System.out.println("equals() → " + obj1.equals(obj2));
        System.out.println("equals(same value) → " + obj1.equals(Integer.valueOf(100)));

        System.out.println("\n===== 3️⃣ Bit Manipulation Methods =====");

        int n = 29; // binary: 11101
        System.out.println("bitCount(29) → " + Integer.bitCount(n));
        System.out.println("highestOneBit(29) → " + Integer.highestOneBit(n));
        System.out.println("lowestOneBit(29) → " + Integer.lowestOneBit(n));
        System.out.println("numberOfLeadingZeros(29) → " + Integer.numberOfLeadingZeros(n));
        System.out.println("numberOfTrailingZeros(29) → " + Integer.numberOfTrailingZeros(n));

        int reversedBits = Integer.reverse(n);
        System.out.println("reverse(29) → " + reversedBits + " (Binary: " + Integer.toBinaryString(reversedBits) + ")");

        int reversedBytes = Integer.reverseBytes(n);
        System.out.println("reverseBytes(29) → " + reversedBytes);

        System.out.println("rotateLeft(29, 2) → " + Integer.rotateLeft(n, 2));
        System.out.println("rotateRight(29, 2) → " + Integer.rotateRight(n, 2));

        System.out.println("\n===== 4️⃣ Utility Methods =====");

        int a = 15, b = 25;
        System.out.println("max(15, 25) → " + Integer.max(a, b));
        System.out.println("min(15, 25) → " + Integer.min(a, b));
        System.out.println("sum(15, 25) → " + Integer.sum(a, b));

        System.out.println("signum(0) → " + Integer.signum(0));
        System.out.println("signum(10) → " + Integer.signum(10));
        System.out.println("signum(-5) → " + Integer.signum(-5));

        System.out.println("hashCode() → " + obj1.hashCode());

        // getInteger(String nm)
        // Setting a system property manually for demonstration
        System.setProperty("myNumber", "12345");
        System.out.println("getInteger(\"myNumber\") → " + Integer.getInteger("myNumber"));
        System.out.println("getInteger(\"notExist\", 999) → " + Integer.getInteger("notExist", 999)); // default value

        System.out.println("\n===== ✅ End of Integer Class Demo =====");
    }
}

