/**
 * Entidade Aluno - guarda os dados encapsulados.
 * Sabe converter-se em linha de texto (toLinha) e voltar a ser objeto (fromLinha).
 * Arquivo correspondente: alunos.txt
 */
public class Aluno implements Persistivel {

    private String nome;
    private String matricula;
    private String cidade;

    public Aluno(String nome, String matricula, String cidade) {
        this.nome = nome;
        this.matricula = matricula;
        this.cidade = cidade;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCidade() {
        return cidade;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * Converte o objeto Aluno em uma linha de texto separada por ponto e vírgula.
     * Formato: nome;matricula;cidade
     */
    public String toLinha() {
        return nome + ";" + matricula + ";" + cidade;
    }

    /**
     * Cria um objeto Aluno a partir de uma linha de texto separada por ponto e vírgula.
     * Formato esperado: nome;matricula;cidade
     */
    public static Aluno fromLinha(String linha) {
        String[] partes = linha.split(";");
        return new Aluno(partes[0], partes[1], partes[2]);
    }
}
