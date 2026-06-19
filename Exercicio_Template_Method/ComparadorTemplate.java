import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

abstract class ComparadorTemplate implements Comparator<String> {
    @Override
    public final int compare(String s1, String s2) {
        String chave1 = extrairChave(s1);
        String chave2 = extrairChave(s2);

        return chave1.compareTo(chave2);
    }

    protected abstract String extrairChave(String s);
}