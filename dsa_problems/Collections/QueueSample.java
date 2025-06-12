package dsa_problems.Collections;

public class QueueSample {

    public static void main(String[] args) {
        
        Queue q  = new Queue();
        
        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);
        q.enqueue(40);
        q.enqueue(50);

        q.printQueue();

        q.dequeue();

        q.printQueue();
        
        q.enqueue(60);
        q.enqueue(70);

        q.printQueue();
    }
    
}
