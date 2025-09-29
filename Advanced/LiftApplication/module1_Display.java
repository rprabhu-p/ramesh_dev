package LiftApplication;

import java.util.List;

public class module1_Display {
    public static void show(List<Lift> lifts) {
        System.out.print("Lift   : ");
        for (Lift l : lifts) System.out.print(l.name + " ");
        System.out.println();

        System.out.print("Floor  : ");
        for (Lift l : lifts) System.out.print((l.underMaintenance ? "-1" : l.currentFloor) + "   ");
        System.out.println();
    }
}
