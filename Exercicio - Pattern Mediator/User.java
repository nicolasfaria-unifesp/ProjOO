package chat;

public abstract class User {

    protected final ChatMediator mediator;
    protected final String name;

    public User(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Envia mensagem privada para destinatário por nome.
    public void sendTo(String recipientName, String message) {
        System.out.printf("[%s → %s]: %s%n", name, recipientName, message);
        mediator.sendMessage(message, this, recipientName);
    }

    // Envia mensagem para todos os participantes do canal.
    public void sendToAll(String message) {
        System.out.printf("[%s → TODOS]: %s%n", name, message);
        mediator.broadcast(message, this);
    }

    // Chamado pelo Mediator quando usuário recebe mensagem.
    public abstract void receive(String message, String senderName);

    // Desconecta o usuário do canal.
    public void disconnect() {
        mediator.disconnect(this);
    }
}
