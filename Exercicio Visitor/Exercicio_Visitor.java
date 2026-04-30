// Interface Visitor
interface FileConverterVisitor{
    void visit(PdfFile pdf);
    void visit(WordFile word);
    void visit(ExcelFile excel);
}

// Interface Element
interface FileElement{
    void accept(FileConverterVisitor visitor);
}

// Elementos Concretos (Arquivos de Origem)
class PdfFile implements FileElement{
    @Override
    public void accept(FileConverterVisitor visitor){
        visitor.visit(this);
    }
}

class WordFile implements FileElement{
    @Override
    public void accept(FileConverterVisitor visitor){
        visitor.visit(this);
    }
}

class ExcelFile implements FileElement{
    @Override
    public void accept(FileConverterVisitor visitor){
        visitor.visit(this);
    }
}

// Visitors Concretos (Arquivos de Destino)
class ToHtmlConverter implements FileConverterVisitor{
    @Override
    public void visit(PdfFile pdf){
        System.out.println("Convertendo arquivo PDF para HTML");
    }

    @Override
    public void visit(WordFile word){
        System.out.println("Convertendo arquivo Word para HTML");
    }

    @Override
    public void visit(ExcelFile excel){
        System.out.println("Convertendo arquivo Excel para HTML");
    }
}

class ToTextConverter implements FileConverterVisitor{
    @Override
    public void visit(PdfFile pdf) {
        System.out.println("Convertendo arquivo PDF para Texto");
    }

    @Override
    public void visit(WordFile word){
        System.out.println("Convertendo arquivo Word para Texto");
    }

    @Override
    public void visit(ExcelFile excel){
        System.out.println("Convertendo arquivo Excel para Texto");
    }
}

class ToImageConverter implements FileConverterVisitor{
    @Override
    public void visit(PdfFile pdf){
        System.out.println("Convertendo arquivo PDF para Imagem");
    }

    @Override
    public void visit(WordFile word){
        System.out.println("Convertendo arquivo Word para Imagem");
    }

    @Override
    public void visit(ExcelFile excel){
        System.out.println("Convertendo arquivo Excel para Imagem");
    }
}

// Classe Principal
public class Exercicio_Visitor{
    public static void main(String[] args){
        // Inicialização dos arquivos
        FileElement pdf = new PdfFile();
        FileElement word = new WordFile();
        FileElement excel = new ExcelFile();

        // Inicialização dos conversores
        FileConverterVisitor htmlConverter = new ToHtmlConverter();
        FileConverterVisitor textConverter = new ToTextConverter();
        FileConverterVisitor imageConverter = new ToImageConverter();

        // Execução das conversões para HTML
        System.out.println("Conversões para HTML");
        pdf.accept(htmlConverter);
        word.accept(htmlConverter);
        excel.accept(htmlConverter);

        // Execução das conversões para Texto
        System.out.println("\nConversões para Texto");
        pdf.accept(textConverter);
        word.accept(textConverter);
        excel.accept(textConverter);

        // Execução das conversões para Imagem
        System.out.println("\nConversões para Imagem");
        pdf.accept(imageConverter);
        word.accept(imageConverter);
        excel.accept(imageConverter);
    }
}
