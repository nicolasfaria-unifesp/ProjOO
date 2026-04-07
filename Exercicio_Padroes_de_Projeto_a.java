// Configurador Central Singleton
class ConfigurationManager{
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
interface Notification{
    void send(String message, String recipient);
}

//Tipos disponíveis de Notificações
class EmailNotification implements Notification{
    @Override
    public void send(String message, String recipient){
        System.out.println("Enviando E-mail para [" + recipient + "]: " + message);
    }
}

class SMSNotification implements Notification{
    @Override
    public void send(String message, String recipient){
        System.out.println("Enviando SMS para [" + recipient + "]: " + message);
    }
}

class FacebookNotification implements Notification{
    @Override
    public void send(String message, String recipient){
        System.out.println("Enviando Mensagem no Facebook para [" + recipient + "]: " + message);
    }
}

// Padrão Factory para instanciar Notificação
class NotificationFactory {
    public static Notification createNotification(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Tipo de notificação não pode ser nulo ou vazio.");
        }
        
        switch (type.toUpperCase()) {
            case "EMAIL":
                return new EmailNotification();
            case "SMS":
                return new SMSNotification();
            case "FACEBOOK":
                return new FacebookNotification();
            default:
                throw new IllegalArgumentException("Tipo de notificação desconhecido: " + type);
        }
    }
}

// Testando estrutura
public class Main{
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

            Notification facebook = NotificationFactory.createNotification("FACEBOOK");
            facebook.send("Você ganhou 20% de desconto no nosso app.", "DeviceToken123");
            
            Notification erro = NotificationFactory.createNotification("WHATSAPP");
            
        }
        catch (IllegalArgumentException e){
            System.err.println("Erro capturado: " + e.getMessage());
        }
    }
}
