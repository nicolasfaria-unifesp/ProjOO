import java.util.HashMap;
import java.util.Map;

// Configurador Central Singleton
class ConfigurationManager {
    private static ConfigurationManager instance;

    private String appName = "MeuSistemaNotificacoes";
    private String server = "smtp.meuservidor.com";
    private int maxRetries = 3;

    private ConfigurationManager(){}

    public static synchronized ConfigurationManager getInstance(){
        if (instance == null){
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String getAppName(){
        return appName;
    }
    public String getServer(){
        return server;
    }
    public int getMaxRetries(){
        return maxRetries;
    }
}

// Interface para Notificação
interface Notification {
    void send(String message, String recipient);
}

//Tipos disponíveis de Notificações
class EmailNotification implements Notification {
    @Override
    public void send(String message, String recipient){
        System.out.println("Enviando E-mail para [" + recipient + "]: " + message);
    }
}

class FacebookNotification implements Notification {
    @Override
    public void send(String message, String recipient){
        System.out.println("Enviando Mensagem no Facebook para [" + recipient + "]: " + message);
    }
}

// ADAPTER

// API externa de SMS com interface incompatível
class ExternalSMSApi {
    public void sendExternal(String phone, String text) {
        System.out.println("ExternalSMSApi: Disparando texto '" + text + "' para o fone " + phone);
    }
}

// Adapter para adequar a API externa à interface Notification
class SMSAdapter implements Notification {
    private ExternalSMSApi externalApi;

    public SMSAdapter(ExternalSMSApi externalApi) {
        this.externalApi = externalApi;
    }

    @Override
    public void send(String message, String recipient) {
        // Adapta os parâmetros recebidos para os esperados pela API externa
        externalApi.sendExternal(recipient, message);
    }
}

// PROXY

// Proxy para intermediar acesso, validando e limitando tentativas
class NotificationProxy implements Notification {
    private Notification realNotification;
    private Map<String, Integer> sendAttempts;

    public NotificationProxy(Notification realNotification) {
        this.realNotification = realNotification;
        this.sendAttempts = new HashMap<>();
    }

    @Override
    public void send(String message, String recipient) {
        System.out.println("[Proxy Log] Intermediando notificação para: " + recipient);

        // Validar permissões
        if (recipient == null || recipient.trim().isEmpty()) {
            System.err.println("[Proxy Log] Bloqueado: Destinatário inválido ou vazio.\n");
            return;
        }

        // Limitar tentativas de envio
        int maxRetries = ConfigurationManager.getInstance().getMaxRetries();
        int attempts = sendAttempts.getOrDefault(recipient, 0);

        if (attempts >= maxRetries) {
            System.err.println("[Proxy Log] Bloqueado: Limite de " + maxRetries + " tentativas excedido para " + recipient + ".\n");
            return;
        }

        // Incrementa o número de envios para este destinatário e registra
        sendAttempts.put(recipient, attempts + 1);

        // Encaminhar para o objeto real se tudo estiver válido
        realNotification.send(message, recipient);
        System.out.println("[Proxy Log] Envio registrado e finalizado com sucesso.\n");
    }
}

// Padrão Factory para instanciar Notificação
class NotificationFactory {
    public static Notification createNotification(String type){
        if (type == null || type.isEmpty()){
            throw new IllegalArgumentException("Tipo de notificação não pode ser nulo ou vazio.");
        }
        
        Notification notification;

        switch (type.toUpperCase()){
            case "EMAIL":
                notification = new EmailNotification();
                break;
            case "SMS":
                // Utilizando o Adapter para instanciar a notificação de SMS
                notification = new SMSAdapter(new ExternalSMSApi());
                break;
            case "FACEBOOK":
                notification = new FacebookNotification();
                break;
            default:
                throw new IllegalArgumentException("Tipo de notificação desconhecido: " + type);
        }

        // Retorna a notificação envolvida pelo Proxy para garantir logs, validações e limites
        return new NotificationProxy(notification);
    }
}

// Testando estrutura
public class Main {
    public static void main(String[] args){
        // Testando o Singleton
        ConfigurationManager config = ConfigurationManager.getInstance();
        System.out.println("Aplicação: " + config.getAppName());
        System.out.println("Servidor: " + config.getServer());
        System.out.println("Tentativas Máximas: " + config.getMaxRetries() + "\n");

        // Testando a Factory
        try{
            Notification email = NotificationFactory.createNotification("EMAIL");
            email.send("Seu pedido foi confirmado!", "cliente@email.com");

            Notification sms = NotificationFactory.createNotification("SMS");
            sms.send("Seu código de verificação é 1234", "(11) 99999-9999");
            
            sms.send("Reenviando código 1234", "(11) 99999-9999");
            sms.send("Tentativa 3", "(11) 99999-9999");
            sms.send("Tentativa 4 - Esta deve ser bloqueada", "(11) 99999-9999"); // Bloqueado pelo Proxy

            Notification facebook = NotificationFactory.createNotification("FACEBOOK");
            facebook.send("Você ganhou 20% de desconto no nosso app.", "DeviceToken123");
            
            // Testando validação do Proxy
            facebook.send("Mensagem sem destinatário", ""); 

            // Vai gerar a exceção esperada
            Notification erro = NotificationFactory.createNotification("WHATSAPP");
            
        }
        catch (IllegalArgumentException e){
            System.err.println("Erro capturado: " + e.getMessage());
        }
    }
}
