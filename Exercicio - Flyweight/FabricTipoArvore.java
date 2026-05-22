public class FabricTipoArvore {
    private static final TipoArvore[] cache = new TipoArvore[50];
    private static int totalTipos = 0;

    public static TipoArvore getTipo(String especie, String textura) {
        if (totalTipos >= 50){
            System.out.println("Limite de tipos de arvores atingido.");
            return null;
        }
        for (int i = 0; i < totalTipos; i++) {
            if (cache[i].getEspecie().equals(especie)) {
                return cache[i];
            }
        }
        TipoArvore novoTipo = new TipoArvore(especie, textura);
        cache[totalTipos++] = novoTipo;
        return novoTipo;
    }
}
