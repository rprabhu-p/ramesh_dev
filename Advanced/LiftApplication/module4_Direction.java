package Advanced.LiftApplication;

import java.util.List;

public class module4_Direction {
    public static Lift resolve(List<Lift> lifts, int from, int to, int bestDist) {
        int userDir = Integer.compare(to, from);
        for (Lift l : lifts) {
            int dist = Math.abs(l.currentFloor - from);
            if (dist == bestDist) {
                int liftDir = Integer.compare(to, l.currentFloor);
                if (liftDir == userDir) return l;
            }
        }
        return null;
    }
}
