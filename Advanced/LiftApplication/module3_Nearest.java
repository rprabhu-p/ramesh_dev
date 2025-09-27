package Advanced.LiftApplication;

import java.util.List;

public class module3_Nearest {
    public static Lift find(List<Lift> lifts, int from, int to) {
        Lift best = null;
        int bestDist = Integer.MAX_VALUE;

        for (Lift l : lifts) {
            if (!l.canServe(from, to)) continue;
            int dist = Math.abs(l.currentFloor - from);
            if (dist < bestDist) {
                bestDist = dist;
                best = l;
            }
        }
        return best;
    }
}
