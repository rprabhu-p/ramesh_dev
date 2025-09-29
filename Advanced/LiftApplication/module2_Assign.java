package LiftApplication;

public class module2_Assign {
    public static void assign(Lift lift, int to) {
        System.out.println(lift.name + " is assigned");
        lift.currentFloor = to;
    }
}
