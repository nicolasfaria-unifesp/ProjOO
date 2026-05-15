package chat;

public interface ChatMediator {

    // Registra participante
    void register(User user);

    // Envia mensagem de remetente para destinatário específico.
    void sendMessage(String message, User sender, String recipientName);

    // Transmite mensagem de remetente para todos os participantes.
    void broadcast(String message, User sender);

    // Remove participante do canal.
    void disconnect(User user);
}
