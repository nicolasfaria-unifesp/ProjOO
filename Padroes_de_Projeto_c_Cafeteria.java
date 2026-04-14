// Classes
interface Beverage {
    String getDescription();
    double getCost();
}

class Espresso implements Beverage {
    public String getDescription() {
        return "Café Expresso";
    }

    public double getCost() {
        return 5.00;
    }
}

class Cappuccino implements Beverage {
    public String getDescription() {
        return "Cappuccino";
    }

    public double getCost() {
        return 7.00;
    }
}

class Tea implements Beverage {
    public String getDescription() {
        return "Chá";
    }

    public double getCost() {
        return 4.00;
    }
}

// Classe Decorator
abstract class AddOnDecorator implements Beverage {
    // Declaração das Classes
    protected Beverage beverage;

    // Construtor para inicializar os componentes
    public AddOnDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public abstract String getDescription();
    public abstract double getCost();
}

class Milk extends AddOnDecorator {
    public Milk(Beverage beverage) {
        super(beverage);
    }

    // Rotina para obter a descrição
    public String getDescription() {
        return beverage.getDescription() + ", Leite";
    }

    // Rotina para obter o custo
    public double getCost() {
        return beverage.getCost() + 1.50;
    }
}

class WhippedCream extends AddOnDecorator {
    public WhippedCream(Beverage beverage) {
        super(beverage);
    }

    // Rotina para obter a descrição
    public String getDescription() {
        return beverage.getDescription() + ", Chantilly";
    }

    // Rotina para obter o custo
    public double getCost() {
        return beverage.getCost() + 2.50;
    }
}

class Cinnamon extends AddOnDecorator {
    public Cinnamon(Beverage beverage) {
        super(beverage);
    }

    // Rotina para obter a descrição
    public String getDescription() {
        return beverage.getDescription() + ", Canela";
    }

    // Rotina para obter o custo
    public double getCost() {
        return beverage.getCost() + 0.75;
    }
}

class ChocolateSyrup extends AddOnDecorator {
    public ChocolateSyrup(Beverage beverage) {
        super(beverage);
    }

    // Rotina para obter a descrição
    public String getDescription() {
        return beverage.getDescription() + ", Calda de Chocolate";
    }

    // Rotina para obter o custo
    public double getCost() {
        return beverage.getCost() + 3.00;
    }
}

// Testando
public class Main {
    public static void main(String[] args) {
        // Declaração dos objetos
        Beverage order1 = new Espresso();
        
        // Declaração do Decorator com os objetos
        order1 = new Milk(order1);
        order1 = new ChocolateSyrup(order1);

        // Teste do pedido 1
        System.out.println("Pedido 1: " + order1.getDescription());
        System.out.printf("Custo Total: R$ %.2f\n\n", order1.getCost());

        // Declaração dos objetos
        Beverage order2 = new Cappuccino();
        
        // Declaração do Decorator com os objetos
        order2 = new WhippedCream(order2);
        order2 = new Cinnamon(order2);
        order2 = new ChocolateSyrup(order2);

        // Teste do pedido 2
        System.out.println("Pedido 2: " + order2.getDescription());
        System.out.printf("Custo Total: R$ %.2f\n\n", order2.getCost());

        // Declaração dos objetos
        Beverage order3 = new Tea();
        
        // Declaração do Decorator com os objetos
        order3 = new Cinnamon(order3);

        // Teste do pedido 3
        System.out.println("Pedido 3: " + order3.getDescription());
        System.out.printf("Custo Total: R$ %.2f\n", order3.getCost());
    }
}
