/**
 * Entidade Turma - guarda os dados encapsulados (desafio opcional, quarta aba).
 * Sabe converter-se em linha de texto (toLinha) e voltar a ser objeto (fromLinha).
 * Arquivo correspondente: turmas.txt
 */
public class Turma {

    private String codigo;
    private String curso;
    private int vagas;

    public Turma(String codigo, String curso, int vagas) {
        this.codigo = codigo;
        this.curso = curso;
        this.vagas = vagas;
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getCurso() {
        return curso;
    }

    public int getVagas() {
        return vagas;
    }

    // Setters
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    /**
     * Converte o objeto Turma em uma linha de texto separada por ponto e vírgula.
     * Formato: codigo;curso;vagas
     */
    public String toLinha() {
        return codigo + ";" + curso + ";" + vagas;
    }

    /**
     * Cria um objeto Turma a partir de uma linha de texto separada por ponto e vírgula.
     * Formato esperado: codigo;curso;vagas
     */
    public static Turma fromLinha(String linha) {
        String[] partes = linha.split(";");
        return new Turma(partes[0], partes[1], Integer.parseInt(partes[2]));
    }
}
