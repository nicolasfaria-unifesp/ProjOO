class Arvore {
    private int qtGalhos;
    private int posicaoX;
    private int posicaoY;
    private float altura;
    private final TipoArvore tipo;

    public ArvorePosicionada(TipoArvore tipo, String especie, String textura, int qtGalhos, int x, int y, float altura) {
        this.tipo = tipo;
        this.qtGalhos = qtGalhos;
        this.posicaoX = x;
        this.posicaoY = y;
        this.altura = altura;
    }

    public void printInformacoes() {
        System.out.println("Renderizando " + getEspecie() + " com a textura " + getTexturaFolha() + " na posição (" + posicaoX + "," + posicaoY + ") com altura " + altura + " e " + qtGalhos + " galhos.");
    }
}