package Advanced.TollApplication;

public class PaymentRecord {
    Vehicle vehicle;
    double amountPaid;

    public PaymentRecord(Vehicle vehicle, double amountPaid) {
        this.vehicle = vehicle;
        this.amountPaid = amountPaid;
    }
}
