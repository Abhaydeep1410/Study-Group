package patterns;

/*
* why to create object again and again
* creational design pattern
* when objet creation is expensive and time consuming
* so we clone objects instead of creating them
* */

/*
* Deep copy recommended
* SHallow Copy                                          Deep Copy
* only copies references inside the class            recursively copies everything inside
* cloning only top                                  safer but heavier
* if anyone changes one inner class might
* affect another
*
*
* */


public class Day1_Prototype {
    public static void main(String[] args) {
        Order order1=new Order(1,"AD","COD");
        Order order2=order1.clone();
        order2.setOrderId(2);
        System.out.println(order1);
        System.out.println(order2);
    }
}

class Order implements Cloneable {
    private int orderId;
    private String companyName;
    private String paymentType;

    public Order(int orderId, String companyName, String paymentType) {
        this.orderId = orderId;
        this.companyName = companyName;
        this.paymentType = paymentType;
    }

    public Order clone(){
        try{
            return (Order)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", companyName='" + companyName + '\'' +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

}


