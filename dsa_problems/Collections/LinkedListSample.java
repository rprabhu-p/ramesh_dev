package dsa_problems.Collections;

public class LinkedListSample {
    public static void main(String[] args) {
        
        LinkedList li = new LinkedList();

        li.add(3);
        li.add(5);
        li.add(8);

        li.addFirst(7);

        li.addLast(6);

        li.delete(5);

        li.printValues();
    }
}
