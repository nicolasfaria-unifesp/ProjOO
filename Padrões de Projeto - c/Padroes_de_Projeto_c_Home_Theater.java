// Classes
class TV {
    public void turnOn() {
        System.out.println("TV ligada.");
    }

    public void turnOff() {
        System.out.println("TV desligada.");
    }
}

class Projector {
    public void turnOn() {
        System.out.println("Projetor ligado.");
    }
    
    public void turnOff() {
        System.out.println("Projetor desligado.");
    }
}

class Receiver {
    public void turnOn() {
        System.out.println("Receiver ligado.");
    }

    public void turnOff() {
        System.out.println("Receiver desligado.");
    }

    public void setVolume(int level) {
        System.out.println("Volume do receiver ajustado para " + level + ".");
    }
}

class MediaPlayer {
    public void turnOn() {
        System.out.println("Player de mídia ligado.");
    }

    public void turnOff() {
        System.out.println("Player de mídia desligado.");
    }

    public void play(String media) {
        System.out.println("Reproduzindo: " + media + ".");
    }
}

class SoundSystem {
    public void turnOn() {
        System.out.println("Sistema de som ligado.");
    }

    public void turnOff() {
        System.out.println("Sistema de som desligado.");
    }

    public void setSurround() {
        System.out.println("Modo surround ativado para imersão.");
    }

    public void setStereo() {
        System.out.println("Modo estéreo ativado para música.");
    }
}

class AmbientLight {
    public void turnOn() {
        System.out.println("Luzes ambiente acesas.");
    }

    public void turnOff() {
        System.out.println("Luzes ambiente apagadas.");
    }

    public void dim() {
        System.out.println("Luzes ambiente dimerizadas (preparando para o filme).");
    }
}

// Classe Facade
class HomeTheaterFacade {
    // Declaração das Classes
    private TV tv;
    private Projector projector;
    private Receiver receiver;
    private MediaPlayer mediaPlayer;
    private SoundSystem soundSystem;
    private AmbientLight ambientLight;

    // Construtor para inicializar os componentes
    public HomeTheaterFacade(TV tv, Projector projector, Receiver receiver, MediaPlayer mediaPlayer, SoundSystem soundSystem, AmbientLight ambientLight) {
        this.tv = tv;
        this.projector = projector;
        this.receiver = receiver;
        this.mediaPlayer = mediaPlayer;
        this.soundSystem = soundSystem;
        this.ambientLight = ambientLight;
    }

    // Rotina para assistir filme
    public void watchMovie(String movieTitle) {
        System.out.println("\n--- Iniciando rotina: Assistir Filme ---");
        ambientLight.dim();
        projector.turnOn();
        receiver.turnOn();
        receiver.setVolume(25);
        soundSystem.turnOn();
        soundSystem.setSurround();
        mediaPlayer.turnOn();
        mediaPlayer.play(movieTitle);
    }

    // Rotina para ouvir música
    public void listenToMusic(String musicTitle) {
        System.out.println("\n--- Iniciando rotina: Ouvir Música ---");
        ambientLight.turnOn();
        tv.turnOn();
        receiver.turnOn();
        receiver.setVolume(15);
        soundSystem.turnOn();
        soundSystem.setStereo();
        mediaPlayer.turnOn();
        mediaPlayer.play(musicTitle);
    }

    // Rotina para desligar todo o sistema
    public void turnOffEverything() {
        System.out.println("\n--- Desligando o Home Theater ---");
        ambientLight.turnOn();
        tv.turnOff();
        projector.turnOff();
        receiver.turnOff();
        soundSystem.turnOff();
        mediaPlayer.turnOff();
        System.out.println("Sistema encerrado com sucesso.");
    }
}

// Testando
public class Main {
    public static void main(String[] args) {
        // Declaração dos objetos
        TV tv = new TV();
        Projector projector = new Projector();
        Receiver receiver = new Receiver();
        MediaPlayer mediaPlayer = new MediaPlayer();
        SoundSystem soundSystem = new SoundSystem();
        AmbientLight ambientLight = new AmbientLight();

        // Declaração da Facade com os objetos
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, projector, receiver, mediaPlayer, soundSystem, ambientLight);

        // Teste da rotina de filme
        homeTheater.watchMovie("O Senhor dos Anéis");
        
        // Teste da rotina de música
        homeTheater.listenToMusic("Daft Punk - Random Access Memories");
        
        // Teste da rotina de desligamento
        homeTheater.turnOffEverything();
    }
}
