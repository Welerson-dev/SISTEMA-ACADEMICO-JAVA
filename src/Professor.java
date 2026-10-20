/**
 * Entidade Professor - guarda os dados encapsulados.
 * Sabe converter-se em linha de texto (toLinha) e voltar a ser objeto (fromLinha).
 * Arquivo correspondente: professores.txt
 *
 * Nota sobre o salário: no arquivo professores.txt o salário é gravado com ponto
 * decimal (ex.: 6200.5), porque o Double.parseDouble exige o padrão americano.
 * Na tela, o usuário digita com vírgula (ex.: 6200,50), como é natural no Brasil.
 * A conversão de vírgula para ponto é feita na TelaAluno antes de chamar o construtor.
 * Isso explica por que o arquivo mostra "6200.5" mesmo tendo digitado "6200,50".
 */
public class Professor {

    private String nome;
    private String titulacao;
    private double salario;

    public Professor(String nome, String titulacao, double salario) {
        this.nome = nome;
        this.titulacao = titulacao;
        this.salario = salario;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getTitulacao() {
        return titulacao;
    }

    public double getSalario() {
        return salario;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    /**
     * Converte o objeto Professor em uma linha de texto separada por ponto e vírgula.
     * Formato: nome;titulacao;salario
     */
    public String toLinha() {
        return nome + ";" + titulacao + ";" + salario;
    }

    /**
     * Cria um objeto Professor a partir de uma linha de texto separada por ponto e vírgula.
     * Formato esperado: nome;titulacao;salario
     */
    public static Professor fromLinha(String linha) {
        String[] partes = linha.split(";");
        return new Professor(partes[0], partes[1], Double.parseDouble(partes[2]));
    }
}
