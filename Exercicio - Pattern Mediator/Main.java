package chat;

// Testando Funcionalidades
public class Main {

    public static void main(String[] args) {

        separator("PADRÃO MEDIATOR — SISTEMA DE CHAT");

        // Criação do Mediator
        ChatChannel canal = new ChatChannel("Sala Principal");

        // Criação dos usuários
        User maria = new ConcreteUser(canal, "Maria");
        User joao   = new ConcreteUser(canal, "João");
        User carol = new ConcreteUser(canal, "Carol");

        System.out.printf("%nParticipantes online: %d%n%n", canal.getOnlineCount());

        separator("1 - Mensagem privada (Maria -> João)");
        maria.sendTo("João", "Oi João! Tudo bem?");

        separator("2 - Resposta privada (João -> Maria)");
        joao.sendTo("Maria", "Oi Maria! Tudo ótimo, e você?");

        separator("3 - Mensagem geral (Carol para todos)");
        carol.sendToAll("Olá a todos! Posso participar da conversa?");

        separator("4 - Resposta para Carol (Maria -> Carol)");
        maria.sendTo("Carol", "Claro, Carol! Bem-vinda!");

        separator("5 - Mensagem para usuário inexistente");
        joao.sendTo("Zé", "Você existe?");

        separator("6 - Tentativa de mensagem para si mesmo");
        maria.sendTo("Maria", "Eco...");

        separator("7 - Desconexão de João");
        joao.disconnect();

        separator("8 - Mensagem geral após saída de João");
        maria.sendToAll("João saiu... só ficamos eu e Carol.");

        separator("9 - Mensagem para João já desconectado");
        carol.sendTo("João", "Volte logo, João!");

        System.out.printf("Participantes online: %d%n", canal.getOnlineCount());
        separator("FIM DA DEMONSTRAÇÃO");
    }

    private static void separator(String title) {
        System.out.println();
        System.out.println("---------------------------------------------------");
        System.out.printf( "│ %-47s │%n", title);
        System.out.println("---------------------------------------------------");
    }
}
