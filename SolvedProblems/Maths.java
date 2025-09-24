
@FunctionalInterface
interface Maths {
    int calculate(int x);
}

class Square {
    public static void main(String[] args) {
        int a= 5;
        Maths s = (int x) -> x*x;
        System.out.println(s.calculate(a));
    }    
}
