import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class ComparadorUltimaLetra extends ComparadorTemplate {
    @Override
    protected String extrairChave(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}