package LiftApplication;

import java.util.List;

public class LiftSystem {
    List<Lift> lifts;

    public LiftSystem() {
        lifts = module5_Restrictions.defaultLifts();
    }

    public void display() {
        module1_Display.show(lifts);
    }

    public void request(int from, int to) {
        Lift nearest = module3_Nearest.find(lifts, from, to);
        if (nearest == null) {
            System.out.println("No available lift!");
            return;
        }

        int bestDist = Math.abs(nearest.currentFloor - from);
        Lift byDirection = module4_Direction.resolve(lifts, from, to, bestDist);
        if (byDirection != null) nearest = byDirection;

        if (!module7_Capacity.hasCapacity(nearest)) {
            System.out.println("Lift full! Try another.");
            return;
        }

        module2_Assign.assign(nearest, to);
        display();
    }

    public void setMaintenance(String liftName, boolean status) {
        for (Lift l : lifts) {
            if (l.name.equals(liftName)) {
                module8_Maintenance.set(l, status);
            }
        }
    }
}
