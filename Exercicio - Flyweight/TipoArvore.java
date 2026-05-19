public abstract class TipoArvore {
    private final String especie;
    private final String texturaFolha;

    public Arvore(String especie, String texturaFolha) {
        this.especie = especie;
        this.texturaFolha = texturaFolha;
    }

    public void printInformacoes() { 
        System.out.println("Espécie: " + this.especie);
        System.out.println("Textura da folha: " + this.texturaFolha);
    }
}