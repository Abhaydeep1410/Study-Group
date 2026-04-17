package patterns;

/*structural design pattern
* add new behaviour dynamically at runtime without changing original structure
* have concrete component on top of which decorator works
* and abstract decorator class which allows child class to implement behaviour
* and concrete decorators
*/

interface Pizza{
    String getDescription();
    int getPrice();
}

//concrete component
class PizzaBase implements Pizza{

    @Override
    public String getDescription() {
        return "pizza base";
    }

    @Override
    public int getPrice() {
        return 50;
    }

}

abstract class PizzaDecorator implements Pizza{
    protected Pizza pizza;
    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }
}

//concrete decorators
class Cheeze extends PizzaDecorator{

    public Cheeze(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return "adding cheeze";
    }

    @Override
    public int getPrice() {
        return pizza.getPrice()+30;
    }
}

class Olives extends PizzaDecorator{

    public Olives(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return "adding olives";
    }

    @Override
    public int getPrice() {
        return pizza.getPrice()+50;
    }


}

class Paneer extends PizzaDecorator{

    public Paneer(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return "adding Paneer";
    }

    @Override
    public int getPrice() {
        return pizza.getPrice()+150;
    }
}



public class Day3_Decorator {
    public static void main(String[] args) {
        Pizza pizza=new PizzaBase();

        Pizza cheeze=new Cheeze(pizza);
        Pizza olviesCheeze=new Olives(cheeze);
//
//        System.out.println(pizza.getDescription());
//        System.out.println(cheeze.getDescription());
        System.out.println(olviesCheeze.getPrice());
    }
}
