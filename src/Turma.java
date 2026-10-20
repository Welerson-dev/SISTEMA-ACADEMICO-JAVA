public class Turma implements Persistivel {

    private String codigo;
    private String curso;
    private int vagas;

    public Turma(String codigo, String curso, int vagas) {
        this.codigo = codigo;
        this.curso = curso;
        this.vagas = vagas;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCurso() {
        return curso;
    }

    public int getVagas() {
        return vagas;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    @Override
    public String toLinha() {
        return codigo + ";" + curso + ";" + vagas;
    }

    public static Turma fromLinha(String linha) {
        String[] partes = linha.split(";");
        return new Turma(partes[0], partes[1], Integer.parseInt(partes[2]));
    }
}
