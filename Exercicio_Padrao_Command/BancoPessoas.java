import java.util.HashMap;
import java.util.Map;

public class BancoPessoas {
    private static Map<Integer, Pessoa> banco = new HashMap<>();
    private static Map<String, Comando> comandos = new HashMap<>();

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Sintaxe: java BancoPessoas <comando> [<args>] / <comando2> ...");
            return;
        }

        inicializarComandos();

        String argumentoCompleto = String.join(" ", args);
        String[] comandosSeparados = argumentoCompleto.split("/");

        for (String cmd : comandosSeparados) {
            String[] partes = cmd.trim().split("\\s+");
            
            if (partes.length == 0 || partes[0].isEmpty()) {
                continue;
            }

            String nomeComando = partes[0];
            Comando comando = comandos.get(nomeComando);

            if (comando == null) {
                System.out.println("Comando desconhecido: " + nomeComando);
                continue;
            }

            String[] subArgs = new String[partes.length - 1];
            System.arraycopy(partes, 1, subArgs, 0, subArgs.length);
            comando.executar(subArgs);
        }
    }

    private static void inicializarComandos() {
        comandos.put("new", args -> {
            if (args.length < 2) {
                return;
            }
            int id = Integer.parseInt(args[0]);
            
            if (banco.containsKey(id)) {
                System.out.println("Já existe uma pessoa cadastrada com este ID.");
                return;
            }
            
            String nome = args[1];
            banco.put(id, new Pessoa(id, nome));
            System.out.println("Pessoa adicionada com sucesso.");
        });

        comandos.put("delete", args -> {
            if (args.length < 1) {
                return;
            }
            int id = Integer.parseInt(args[0]);
            
            if (banco.containsKey(id)) {
                banco.remove(id);
                System.out.println("Pessoa removida com sucesso.");
            } else {
                System.out.println("Pessoa nao encontrada.");
            }
        });

        comandos.put("get", args -> {
            if (args.length < 1) {
                return;
            }
            int id = Integer.parseInt(args[0]);
            Pessoa p = banco.get(id);
            
            if (p != null) {
                System.out.println("Pessoa cadastrada:");
                System.out.println("id: " + p.getId() + " Nome: " + p.getNome());
            } else {
                System.out.println("Pessoa nao encontrada.");
            }
        });

        comandos.put("all", args -> {
            if (banco.isEmpty()) {
                System.out.println("Nenhuma pessoa cadastrada no banco.");
            } else {
                System.out.println("Todos as pessoas cadastradas:");
                for (Pessoa p : banco.values()) {
                    System.out.println("id: " + p.getId() + " Nome: " + p.getNome());
                }
            }
        });
    }
}