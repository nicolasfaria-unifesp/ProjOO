public class FabricArvore {
    private static final Arvore[] cacheArvores = new Arvore[1000];
    private static int totalArvores = 0;

    public static Arvore getArvore(String especie, String textura, int qtGalhos, int x, int y, float altura) {
        TipoArvore tipoCompartilhado = FabricTipoArvore.getTipo(especie, textura);
        
        if (tipoCompartilhado == null) {
            return null;
        }

        if (totalArvores >= 1000){
            System.out.println("Limite de arvores atingido.");
            return null;
        } 
        for (int i = 0; i < totalArvores; i++) {
            Arvore a = cacheArvores[i];
            if (a.getTipo() == tipoCompartilhado && a.getAltura() == altura && a.getQtGalhos() == qtGalhos && a.getX() == x && a.getY() == y) {
                return a;
            }
        }

        Arvore novaArvore = new Arvore(tipoCompartilhado, qtGalhos, x, y, altura);
        cacheArvores[totalArvores++] = novaArvore;
        return novaArvore;
    }
}
