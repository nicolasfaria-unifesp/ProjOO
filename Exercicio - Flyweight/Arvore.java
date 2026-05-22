public class Arvore {
    private final int qtGalhos;
    private final int posicaoX;
    private final int posicaoY;
    private final float altura;
    private final TipoArvore tipo;

    public Arvore(TipoArvore tipo, int qtGalhos, int x, int y, float altura) {
        this.tipo = tipo;
        this.qtGalhos = qtGalhos;
        this.posicaoX = x;
        this.posicaoY = y;
        this.altura = altura;
    }

    public TipoArvore getTipo() { return tipo; }
    public float getAltura() { return altura; }
    public int getQtGalhos() { return qtGalhos; }
    public int getX() { return posicaoX; }
    public int getY() { return posicaoY; }

    public void printInformacoes() {
        System.out.println("Renderizando " + tipo.getEspecie() + " com a textura " + tipo.getTexturaFolha() + " na posicao (" + posicaoX + "," + posicaoY + ") com altura " + altura + " e " + qtGalhos + " galhos.");
    }
}
