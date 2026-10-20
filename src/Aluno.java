public class Aluno implements Persistivel {

    private String nome;
    private String matricula;
    private String cidade;

    public Aluno(String nome, String matricula, String cidade) {
        this.nome = nome;
        this.matricula = matricula;
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCidade() {
        return cidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toLinha() {
        return nome + ";" + matricula + ";" + cidade;
    }

    public static Aluno fromLinha(String linha) {
        String[] partes = linha.split(";");
        return new Aluno(partes[0], partes[1], partes[2]);
    }
}
