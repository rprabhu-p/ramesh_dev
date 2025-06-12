package dsa_problems.Collections;

public class Stack {

    static int[] arr = new int[6];
    int top;
    int size;

    Stack() {
        size = arr.length;
        top = -1;
    }

    public void push(int data) {

        top++;
        if(top < size)
            arr[top] = data;
        else 
            System.out.println("Stack overflow");
    }

    public int pop(){
        if(top > -1)
            return arr[top--];
        else 
            System.out.println("Stack underflow");

        return 0;
    }

    public int peek(){
        return arr[top];
    }

    public int isEmpty(){
        return (top<0) ? 1 : 0;
    }

    public void printStack(){
        for(int n : arr){
            System.out.print(n + " ");
        }
        System.out.println();
    }
}
