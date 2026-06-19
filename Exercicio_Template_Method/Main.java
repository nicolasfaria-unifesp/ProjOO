import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> palavras = new ArrayList<>();
        palavras.add("Java");
        palavras.add("Python");
        palavras.add("C++");
        palavras.add("Ama");
        palavras.add("Brasil");

        palavras.sort(new ComparadorUltimaLetra());

        for(String palavra : palavras) {
            System.out.println(palavra);
        }
    }
}