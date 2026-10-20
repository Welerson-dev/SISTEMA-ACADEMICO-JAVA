/**
 * Entidade Disciplina - guarda os dados encapsulados.
 * Sabe converter-se em linha de texto (toLinha) e voltar a ser objeto (fromLinha).
 * Arquivo correspondente: disciplinas.txt
 */
public class Disciplina implements Persistivel {

    private String nome;
    private int cargaHoraria;
    private String periodo;

    public Disciplina(String nome, int cargaHoraria, String periodo) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.periodo = periodo;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public String getPeriodo() {
        return periodo;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * Converte o objeto Disciplina em uma linha de texto separada por ponto e vírgula.
     * Formato: nome;cargaHoraria;periodo
     */
    public String toLinha() {
        return nome + ";" + cargaHoraria + ";" + periodo;
    }

    /**
     * Cria um objeto Disciplina a partir de uma linha de texto separada por ponto e vírgula.
     * Formato esperado: nome;cargaHoraria;periodo
     */
    public static Disciplina fromLinha(String linha) {
        String[] partes = linha.split(";");
        return new Disciplina(partes[0], Integer.parseInt(partes[1]), partes[2]);
    }
}
