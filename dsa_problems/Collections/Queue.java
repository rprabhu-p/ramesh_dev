package dsa_problems.Collections;

public class Queue {

    private int[] arr = new int[5];
    int front;
    int rear;
    int size;

    Queue(){
        size = 0;
        front = 0;
        rear = -1;
    }

    public void enqueue(int data) {
        if(!isFull()) {
            //rear = rear+1; // single queue 
            rear = (rear + 1) % (arr.length);  // circular queue
            arr[rear] = data;
            size++;
        } else {
            System.out.println("Queue is Full!");
        }
    }

    public int dequeue() {
        if(!isEmpty()) {
            int data =  arr[front];
            //front++ ;  //single queue
            front = (front + 1) % (arr.length);   // Circular queue
            size--;
            return data;
        } else {
            System.out.println("Queue is Empty!");
        }
        return 0;
    }

    public boolean isFull(){
        return size == 5;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printQueue() {

        for (int i = front; i<size ; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
