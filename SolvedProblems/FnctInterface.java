
@FunctionalInterface
interface FnctInterface {
    int calculate(int x);
}

class Square {
    public static void main(String[] args) {
        int a= 5;
        FnctInterface s = (int x) -> x*x;
        System.out.println(s.calculate(a));
    }    
}
