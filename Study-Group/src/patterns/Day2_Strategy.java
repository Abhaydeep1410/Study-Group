package patterns;


/*
* Behavioural patter
* Defines how we can change behaviour of a object at runtime without changing its class
* Ex. Payment*/

interface Payment {
    void pay();
}

class PayU implements Payment {
    @Override
    public void pay() {
        System.out.println("Payment by PayU");
    }
}
class RazorPay implements Payment {
    @Override
    public void pay() {
        System.out.println("Payment by RazorPay");
    }
}

class PaymentStrategy{
    Payment payment;
    public PaymentStrategy(Payment payment) {
        this.payment = payment;
    }
    public void setStrategy(Payment payment) {
        this.payment = payment;
    }
    public void getPayment() {
        payment.pay();
    }
}



public class Day2_Strategy {
    public static void main(String[] args) {
        PaymentStrategy paymentStrategy = new PaymentStrategy(new PayU());
        paymentStrategy.getPayment();
        paymentStrategy.setStrategy(new RazorPay());
        paymentStrategy.getPayment();

    }
}
