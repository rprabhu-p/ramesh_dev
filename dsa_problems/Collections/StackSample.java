package dsa_problems.Collections;

public class StackSample {
    public static void main(String[] args) {
        Stack st = new Stack();
        st.pop();   
        st.push(10);
        st.push(20);
        st.push(30);
        st.push(40);
        System.out.println(st.pop()); 
        st.push(50);
        st.push(60);
        System.out.println(st.pop());   

        st.printStack();
    }
}
