package chat;

public class ConcreteUser extends User {

    // Criando usuário.
    public ConcreteUser(ChatMediator mediator, String name) {
        super(mediator, name);
        mediator.register(this);
        System.out.printf("* %s entrou no canal.%n", name);
    }

    // Recebendo mensagem.
    @Override
    public void receive(String message, String senderName) {
        System.out.printf("  L [%s recebeu de %s]: %s%n", name, senderName, message);
    }
}
