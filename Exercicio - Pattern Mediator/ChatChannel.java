package chat;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChatChannel implements ChatMediator {

    private final String channelName;

    // Mapa nome usuário para busca O(1).
    private final Map<String, User> participants = new LinkedHashMap<>();

    public ChatChannel(String channelName) {
        this.channelName = channelName;
        System.out.printf(" - Canal '%s' criado - %n%n", channelName);
    }

    // Registrar e desconectar.
    @Override
    public void register(User user) {
        participants.put(user.getName(), user);
        notifyAll(String.format("*** %s entrou no canal ***", user.getName()), user);
    }

    @Override
    public void disconnect(User user) {
        if (participants.remove(user.getName()) != null) {
            System.out.printf("X  %s saiu do canal.%n", user.getName());
            broadcast(String.format("*** %s deixou o canal ***", user.getName()), user);
        }
    }

    // Enviar mensagens.
    @Override
    public void sendMessage(String message, User sender, String recipientName) {
        User recipient = participants.get(recipientName);

        if (recipient == null) {
            System.out.printf("  [SISTEMA] Usuário '%s' não encontrado ou desconectado.%n",
                    recipientName);
            return;
        }

        if (recipient == sender) {
            System.out.println("  [SISTEMA] Você não pode enviar uma mensagem para si mesmo.");
            return;
        }

        recipient.receive(message, sender.getName());
    }

    @Override
    public void broadcast(String message, User sender) {
        for (User u : participants.values()) {
            if (u != sender) {
                u.receive(message, sender.getName());
            }
        }
    }

    // Auxiliar interno que notifica todos menos o usuário gerador do evento
    private void notifyAll(String systemMessage, User except) {
        for (User u : participants.values()) {
            if (u != except) {
                u.receive(systemMessage, "SISTEMA");
            }
        }
    }

    public String getChannelName() {
        return channelName;
    }

    public int getOnlineCount() {
        return participants.size();
    }
}
