public class TipoArvore {
    private final String especie;
    private final String texturaFolha;

    public TipoArvore(String especie, String texturaFolha) {
        this.especie = especie;
        this.texturaFolha = texturaFolha;
    }

    public String getEspecie() { 
        return especie;
    }
    public String getTexturaFolha() {
        return texturaFolha;
    }
}
