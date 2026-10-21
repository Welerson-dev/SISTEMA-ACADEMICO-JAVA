public class Disciplina {

    private String nome;
    private int cargaHoraria;
    private String periodo;

    public Disciplina(String nome, int cargaHoraria, String periodo) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.periodo = periodo;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String toLinha() {
        return nome + ";" + cargaHoraria + ";" + periodo;
    }

    public static Disciplina fromLinha(String linha) {
        String[] partes = linha.split(";");
        return new Disciplina(partes[0], Integer.parseInt(partes[1]), partes[2]);
    }
}
