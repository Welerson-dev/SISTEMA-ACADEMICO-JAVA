public class Professor implements Persistivel {

    private String nome;
    private String titulacao;
    private double salario;

    public Professor(String nome, String titulacao, double salario) {
        this.nome = nome;
        this.titulacao = titulacao;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public String getTitulacao() {
        return titulacao;
    }

    public double getSalario() {
        return salario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toLinha() {
        return nome + ";" + titulacao + ";" + salario;
    }

    public static Professor fromLinha(String linha) {
        String[] partes = linha.split(";");
        return new Professor(partes[0], partes[1], Double.parseDouble(partes[2]));
    }
}
