package Advanced.TollApplication;

import java.util.*;

public class TollBooth {
    String tollName;
    Map<String, Double> chargeScheme = new HashMap<>(); // VehicleType -> Rate
    List<PaymentRecord> payments = new ArrayList<>();

    public TollBooth(String tollName) {
        this.tollName = tollName;
    }

    public void setCharge(String vehicleType, double rate) {
        chargeScheme.put(vehicleType, rate);
    }

    public double calculateCharge(Vehicle v) {
        double base = chargeScheme.getOrDefault(v.type, 0.0);
        if (v.isVIP) return base * 0.8; // 20% discount
        return base;
    }

    public void recordPayment(Vehicle v, double amount) {
        payments.add(new PaymentRecord(v, amount));
    }

    @Override
    public String toString() {
        return tollName;
    }
}
