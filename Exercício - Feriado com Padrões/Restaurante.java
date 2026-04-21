import java.util.ArrayList;
import java.util.List;

// SINGLETON - Configuração global do restaurante
class RestaurantConfig {
    private static RestaurantConfig instance;
    private String restaurantName = "Design Patterns Grill";
    private boolean isOpen = true;

    private RestaurantConfig() {}

    public static synchronized RestaurantConfig getInstance() {
        if (instance == null) {
            instance = new RestaurantConfig();
        }
        return instance;
    }

    public String getRestaurantName() { return restaurantName; }
    public boolean isOpen() { return isOpen; }
    public void setOpen(boolean isOpen) { this.isOpen = isOpen; }
}

// STRATEGY - Formas de pagamento intercambiáveis
interface PaymentStrategy {
    void pay(double amount);
}

class PixPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.printf("Pagamento de R$ %.2f recebido via PIX.\n", amount);
    }
}

class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.printf("Pagamento de R$ %.2f aprovado no Cartão de Crédito.\n", amount);
    }
}

// OBSERVER - Notificação de status do pedido
interface Observer {
    void update(String status);
}

class Customer implements Observer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String status) {
        System.out.println("[Notificação] Olá " + name + ", seu pedido agora está: " + status);
    }
}

// Sujeito que será observado pelos clientes
class OrderSubject {
    private List<Observer> observers = new ArrayList<>();
    private String status;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void setStatus(String status) {
        this.status = status;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(status);
        }
    }
}

// DECORATOR - Adicionais para os pratos
interface Dish {
    String getDescription();
    double getCost();
}

class Burger implements Dish {
    @Override
    public String getDescription() { return "Hamburguer Clássico"; }
    @Override
    public double getCost() { return 25.00; }
}

class Pizza implements Dish {
    @Override
    public String getDescription() { return "Pizza Margherita"; }
    @Override
    public double getCost() { return 40.00; }
}

abstract class DishDecorator implements Dish {
    protected Dish dish;

    public DishDecorator(Dish dish) {
        this.dish = dish;
    }

    public abstract String getDescription();
    public abstract double getCost();
}

class ExtraCheese extends DishDecorator {
    public ExtraCheese(Dish dish) { super(dish); }

    @Override
    public String getDescription() { return dish.getDescription() + " + Extra Queijo"; }
    @Override
    public double getCost() { return dish.getCost() + 5.00; }
}

class Bacon extends DishDecorator {
    public Bacon(Dish dish) { super(dish); }

    @Override
    public String getDescription() { return dish.getDescription() + " + Bacon"; }
    @Override
    public double getCost() { return dish.getCost() + 7.50; }
}

// FACTORY - Criação de pratos base
class DishFactory {
    public static Dish createDish(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Tipo de prato inválido.");
        }
        switch (type.toUpperCase()) {
            case "BURGER": return new Burger();
            case "PIZZA":  return new Pizza();
            default:       throw new IllegalArgumentException("Prato não existe no cardápio: " + type);
        }
    }
}

// ADAPTER - Integração com API externa de entregas
// API externa com métodos incompatíveis com o nosso sistema
class ExternalDeliveryApi {
    public void dispatchDriver(String driverTask, String location) {
        System.out.println("[UberEats API] Motorista a caminho de '" + location + "' para entregar: " + driverTask);
    }
}

interface DeliveryService {
    void deliver(Dish dish, String address);
}

// Adaptador para usar a API Externa na nossa interface DeliveryService
class DeliveryAdapter implements DeliveryService {
    private ExternalDeliveryApi externalApi;

    public DeliveryAdapter(ExternalDeliveryApi externalApi) {
        this.externalApi = externalApi;
    }

    @Override
    public void deliver(Dish dish, String address) {
        externalApi.dispatchDriver(dish.getDescription(), address);
    }
}

// PROXY - Validação e controle de pedidos
interface OrderProcessor {
    void process(Dish dish, PaymentStrategy payment);
}

class RealOrderProcessor implements OrderProcessor {
    @Override
    public void process(Dish dish, PaymentStrategy payment) {
        System.out.println("Processando prato: " + dish.getDescription());
        payment.pay(dish.getCost());
    }
}

class OrderProxy implements OrderProcessor {
    private RealOrderProcessor realProcessor;

    public OrderProxy() {
        this.realProcessor = new RealOrderProcessor();
    }

    @Override
    public void process(Dish dish, PaymentStrategy payment) {
        // Regra 1: Valida se o restaurante está aberto usando o Singleton
        if (!RestaurantConfig.getInstance().isOpen()) {
            System.err.println("[Proxy Log] Bloqueado: O restaurante está fechado no momento.");
            return;
        }

        // Regra 2: Validação de segurança básica
        if (dish.getCost() <= 0) {
            System.err.println("[Proxy Log] Bloqueado: Prato com valor inválido.");
            return;
        }

        // Encaminha para o processador real se tudo estiver certo
        realProcessor.process(dish, payment);
    }
}

// FACADE - Simplificando o fluxo do restaurante
class RestaurantFacade {
    private OrderProcessor processor;
    private DeliveryService delivery;

    public RestaurantFacade() {
        this.processor = new OrderProxy(); // Uso do Proxy
        this.delivery = new DeliveryAdapter(new ExternalDeliveryApi()); // Uso do Adapter
    }

    // Rotina principal que esconde a complexidade de ligar as classes
    public void placeOrder(String customerName, Dish dish, PaymentStrategy payment, String address) {
        System.out.println("\n=== Novo Pedido Registrado ===");
        System.out.println("Cliente: " + customerName);

        // Se o restaurante estiver fechado, o proxy barra no processamento. 
        // Verificamos o Singleton aqui apenas para parar a execução da Facade.
        if (!RestaurantConfig.getInstance().isOpen()) {
            processor.process(dish, payment); // Dispara a mensagem de erro do Proxy
            return;
        }

        // Configurando o Observer
        OrderSubject orderSubject = new OrderSubject();
        Customer customer = new Customer(customerName);
        orderSubject.addObserver(customer);

        // Cobrando e Processando (Strategy e Proxy)
        processor.process(dish, payment);
        
        // Atualizando Status (Observer)
        orderSubject.setStatus("Sendo preparado na cozinha.");
        orderSubject.setStatus("Aguardando entregador.");

        // Despachando (Adapter)
        delivery.deliver(dish, address);

        orderSubject.setStatus("Entregue!");
        System.out.println("==============================");
    }
}

// Testando todo o ecossistema
public class Restaurante {
    public static void main(String[] args) {
        // Inicializando o Singleton
        RestaurantConfig config = RestaurantConfig.getInstance();
        System.out.println("Bem-vindo ao " + config.getRestaurantName() + "!\n");

        // Instanciando a Facade
        RestaurantFacade facade = new RestaurantFacade();

        // PEDIDO 1: Hamburguer com Bacon via PIX
        Dish order1 = DishFactory.createDish("BURGER"); // Factory
        order1 = new Bacon(order1); // Decorator
        PaymentStrategy payment1 = new PixPayment(); // Strategy
        
        facade.placeOrder("João", order1, payment1, "Rua das Flores, 123");

        // PEDIDO 2: Pizza Margherita com Extra Queijo e Bacon via Cartão de Crédito
        Dish order2 = DishFactory.createDish("PIZZA");
        order2 = new ExtraCheese(order2);
        order2 = new Bacon(order2);
        PaymentStrategy payment2 = new CreditCardPayment();
        
        facade.placeOrder("Maria", order2, payment2, "Av. Paulista, 1000");

        // Fechando o restaurante para testar o Proxy
        System.out.println("\n[Sistema] Fechando o restaurante...");
        config.setOpen(false);

        // PEDIDO 3: Tentativa de pedido com o restaurante fechado
        Dish order3 = DishFactory.createDish("BURGER");
        facade.placeOrder("Carlos", order3, payment1, "Rua ABC, 45");
    }
}
