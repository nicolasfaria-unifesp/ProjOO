public class Main {
    public static void main(String[] args) {
        int totalInsercoes = 100_000;
        int variacoesMaximas = 500; 

        System.out.println("-- SIMULADOR DE FLORESTA - DEMONSTRACAO FLYWEIGHT --\n");

        limparMemoria();
        long memAntesSem = getMemoriaUsada();
        
        Arvore[] florestaSemFlyweight = new Arvore[totalInsercoes];
        for (int i = 0; i < totalInsercoes; i++) {
            int id = i % variacoesMaximas;
            TipoArvore tipo = new TipoArvore("Ipe Amarelo " + (id % 5), "textura_folha_v" + (id % 5));
            florestaSemFlyweight[i] = new Arvore(tipo, 15 + (id % 10), id, id * 2, 4.5f + (id % 3));
        }
        
        long memDepoisSem = getMemoriaUsada();
        long totalSemFlyweight = Math.max(0, memDepoisSem - memAntesSem);

        limparMemoria();
        long memAntesCom = getMemoriaUsada();
        
        Arvore[] florestaComFlyweight = new Arvore[totalInsercoes];
        for (int i = 0; i < totalInsercoes; i++) {
            int id = i % variacoesMaximas;
            florestaComFlyweight[i] = FabricArvore.getArvore(
                "Ipe Amarelo " + (id % 5), 
                "textura_folha_v" + (id % 5), 
                15 + (id % 10), 
                id, 
                id * 2, 
                4.5f + (id % 3)
            );
        }
        
        long memDepoisCom = getMemoriaUsada();
        long totalComFlyweight = Math.max(0, memDepoisCom - memAntesCom);

        System.out.println("--- Demonstracao de Renderizacao (Amostra):");
        for (int i = 0; i < 3; i++) {
            System.out.print("Arvore [" + i + "] -> ");
            florestaComFlyweight[i].printInformacoes();
        }
        
        exibirRelatorio(totalInsercoes, variacoesMaximas, totalSemFlyweight, totalComFlyweight);
    }

    private static long getMemoriaUsada() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static void limparMemoria() {
        for (int i = 0; i < 3; i++) {
            System.gc();
            try { Thread.sleep(50); } catch (InterruptedException ignored) {}
        }
    }

    private static void exibirRelatorio(int total, int varMax, long memSem, long memCom) {
        System.out.println("------ RELATORIO DE CONSUMO DE MEMORIA RAM ------\n");
        System.out.println("Total de arvores renderizadas na tela: " + total);
        System.out.println("Total de variacoes unicas (Combinacoes): " + varMax);
        System.out.println("--------------------------------------------------");
        
        System.out.printf("RAM aproximada ocupada SEM Flyweight: %.2f MB%n", memSem / (1024.0 * 1024.0));
        System.out.printf("RAM aproximada ocupada COM Flyweight: %.2f MB%n", memCom / (1024.0 * 1024.0));
        System.out.println("--------------------------------------------------");
    }
}