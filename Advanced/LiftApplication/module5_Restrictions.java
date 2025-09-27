package Advanced.LiftApplication;

import java.util.ArrayList;
import java.util.List;

public class module5_Restrictions {
    public static List<Lift> defaultLifts() {
        List<Lift> lifts = new ArrayList<>();
        lifts.add(new Lift("L1", 0, 5, 5));
        lifts.add(new Lift("L2", 0, 5, 5));
        lifts.add(new Lift("L3", 6, 10, 6));
        lifts.add(new Lift("L4", 6, 10, 6));
        lifts.add(new Lift("L5", 0, 10, 10));
        return lifts;
    }
}
