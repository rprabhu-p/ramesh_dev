package Advanced.LiftApplication;

public class module8_Maintenance {
    public static void set(Lift lift, boolean status) {
        lift.underMaintenance = status;
        if (status) lift.currentFloor = -1;
        else lift.currentFloor = 0;
    }
}
