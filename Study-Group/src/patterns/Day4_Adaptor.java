package patterns;

/*
* its a structural desing pattern which allows two incompatible interface or class to work with
* each other
* it acts as a bridge between interface a client expects and actual interface of exisiting class*/

interface PaymentGateway{
    void pay();
}

class PayUPayment implements PaymentGateway{

    @Override
    public void pay() {
        System.out.println("Pay U");
    }
}

class PayPal {
    public void pay(){
        System.out.println("Pay Pal pay");
    }
}

class PayPalAdaptor implements PaymentGateway{
    private final PayPal paypal ;

    public PayPalAdaptor(){
        paypal =new PayPal();
    }
    @Override
    public void pay() {
        paypal.pay();
    }
}
public class Day4_Adaptor {
    public static void main(String[] args) {
        PaymentGateway pg = new PayUPayment();
        pg.pay();
        PaymentGateway pg2 = new PayPalAdaptor();
        pg2.pay();
    }
}
