package com.example.collections.list;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Stack;

public class MyStack {

    /*
    * Stack LIFO (Last In First Out) data structure follow karta hai, aur Vector ke sabhi methods + apne specific methods deta hai.
      Stack Class Methods
🔹    Specific Methods of Stack:
      push(E item) → Element stack ke top par add karega.
      pop() → Top element remove karega aur return karega.
      peek() → Sirf top element return karega (remove nahi karega).
      search(Object o) → Element ki position return karega (1-based index from top, agar nahi mila toh -1).
      empty() → true return karega agar stack khali hai.
   */
    public static void main(String[] args) {
        // Create Stack
        Stack<String> stack = new Stack<>();

        // ✅ Add elements using push
        stack.push("Apple");
        stack.push("Banana");
        stack.push("Cherry");
        stack.push("Mango");

        System.out.println("Initial Stack: " + stack);

        // ✅ Peek element (top of stack)
        System.out.println("Peek (Top element): " + stack.peek());

        // ✅ Pop element (remove top)
        System.out.println("Pop (Removed element): " + stack.pop());
        System.out.println("After Pop: " + stack);

        // ✅ Search element
        int pos = stack.search("Banana");
        System.out.println("Position of Banana from top: " + pos);

        // ✅ Check empty
        System.out.println("Is stack empty? " + stack.empty());

        // ✅ Access element by index (Vector method)
        System.out.println("Element at index 1: " + stack.get(1));

        // ✅ Replace element (Vector method)
        stack.set(1, "Kiwi");
        System.out.println("After replacing index 1 with Kiwi: " + stack);

        // ✅ Insert element at specific index (Vector method)
        stack.add(2, "Grapes");
        System.out.println("After inserting Grapes at index 2: " + stack);

        // ✅ Remove element by index
        stack.remove(2);
        System.out.println("After removing element at index 2: " + stack);

        // ✅ Contains check
        System.out.println("Contains Mango? " + stack.contains("Mango"));

        // ✅ Iterate over stack
        System.out.println("\nIterating using for-each:");
        for (String fruit : stack) {
            System.out.println(fruit);
        }

        System.out.println("\nIterating using Iterator:");
        Iterator<String> itr = stack.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        System.out.println("\nIterating using Enumeration:");
        Enumeration<String> en = stack.elements();
        while (en.hasMoreElements()) {
            System.out.println(en.nextElement());
        }

        // ✅ Size of stack
        System.out.println("Size of stack: " + stack.size());

        // ✅ Clear stack
        stack.clear();
        System.out.println("After clearing: " + stack);
    }
}
